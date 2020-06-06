package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.dao.IMealDao;
import ru.javawebinar.topjava.dao.MealDaoMemoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MealService implements IMealService {
    private final static LocalTime startTime = LocalTime.MIN;
    private final static LocalTime endTime = LocalTime.MAX;
    private final static int caloriesPerDay = 2000;
    private final IMealDao mealDao;

    public MealService() {
        this.mealDao = new MealDaoMemoryImpl();
    }

    @Override
    public void saveMeal(HttpServletRequest request) {
        Meal meal = getMealFromRequest(request);
        if (meal.getId() == null) {
            create(meal);
        } else {
            update(meal);
        }
    }

    private Meal getMealFromRequest(HttpServletRequest request) {
        String id = request.getParameter("mealId");
        Long mealId = null;
        if (id != null && !id.isEmpty()) {
            mealId = Long.parseLong(id);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), formatter);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        return new Meal(mealId, dateTime, description, calories);
    }

    @Override
    public Meal create(Meal meal) {
        return mealDao.create(meal);
    }

    @Override
    public Meal update(Meal meal) {
        return mealDao.update(meal);
    }

    @Override
    public List<MealTo> getAll() {
        return convert(mealDao.getAll());
    }

    private List<MealTo> convert(List<Meal> meals) {
        return MealsUtil.filteredByStreams(meals, startTime, endTime, caloriesPerDay);
    }

    @Override
    public void delete(Long id) {
        mealDao.delete(id);
    }

    @Override
    public Meal getById(Long id) {
        return mealDao.getById(id);
    }
}
