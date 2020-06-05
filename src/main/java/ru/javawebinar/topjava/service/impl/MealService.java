package ru.javawebinar.topjava.service.impl;

import ru.javawebinar.topjava.dao.IMealDao;
import ru.javawebinar.topjava.dao.impl.MealDaoMemoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.IMealService;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), formatter);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        return new Meal(mealId, dateTime, description, calories);
    }

    private void create(Meal meal) {
        mealDao.create(meal);
    }

    private void update(Meal meal) {
        mealDao.update(meal);
    }

    @Override
    public List<MealTo> getAll() {
        return convert(mealDao.getAll());
    }

    private List<MealTo> convert(List<Meal> meals) {
        return MealsUtil.filteredByStreams(meals, startTime, endTime, caloriesPerDay);
    }

    @Override
    public void delete(Long mealId) {
        mealDao.delete(mealId);
    }

    @Override
    public Meal getById(Long mealId) {
        return mealDao.getById(mealId);
    }

    @Override
    public void initDB() {
        mealDao.initDB();
    }
}
