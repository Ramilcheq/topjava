package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000)
                .forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime,
            LocalTime endTime, int caloriesPerDay) {
        List<UserMeal> filteredUserMeals = new ArrayList<>();
        Map<LocalDate, Integer> exceedDates = new HashMap<>();
        int dailyCalories;
        for (UserMeal userMeal : meals) {
            LocalDate mealDate = userMeal.getDateTime().toLocalDate();
            dailyCalories = exceedDates.getOrDefault(mealDate, 0) + userMeal.getCalories();
            exceedDates.put(mealDate, dailyCalories);

            LocalTime mealTime = userMeal.getDateTime().toLocalTime();
            if (!(mealTime.isBefore(startTime) || mealTime.isAfter(endTime))) {
                filteredUserMeals.add(userMeal);
            }
        }

        List<UserMealWithExcess> result = new ArrayList<>();
        for (UserMeal userMeal : filteredUserMeals) {
            result.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                    exceedDates.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay));
        }
        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime,
            LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> exceedMap = new HashMap<>();
        return meals.stream()
                .peek(meal -> {
                    LocalDate mealDate = meal.getDateTime().toLocalDate();
                    int dailyCalories = exceedMap.getOrDefault(mealDate, 0) + meal.getCalories();
                    exceedMap.put(mealDate, dailyCalories);
                })
                .filter(meal -> {
                    LocalTime mealTime = meal.getDateTime().toLocalTime();
                    return !(mealTime.isBefore(startTime) || mealTime.isAfter(endTime));
                })
                .map(meal -> {
                    boolean exceed = exceedMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay;
                    return new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                            exceed);
                })
                .collect(Collectors.toList());
    }
}
