package ru.javawebinar.topjava.dao.impl;

import ru.javawebinar.topjava.dao.IMealDao;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class MealDaoMemoryImpl implements IMealDao {
    private static final AtomicLong serialVersionUID = new AtomicLong(0L);
    public static List<Meal> mealsInMemory = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void initDB() {
        deleteAll();
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    private void deleteAll() {
        mealsInMemory.clear();
    }

    @Override
    public void create(Meal meal) {
        meal.setId(serialVersionUID.incrementAndGet());
        mealsInMemory.add(meal);
    }

    @Override
    public void update(Meal meal) {
        Meal mealFromMemory = getById(meal.getId());
        mealFromMemory.setDateTime(meal.getDateTime());
        mealFromMemory.setDescription(meal.getDescription());
        mealFromMemory.setCalories(meal.getCalories());
    }

    @Override
    public Meal getById(Long id) {
        for (Meal meal : mealsInMemory) {
            if (id.equals(meal.getId())) {
                return meal;
            }
        }
        return null;
    }

    @Override
    public List<Meal> getAll() {
        return mealsInMemory;
    }

    @Override
    public void delete(Long mealId) {
        mealsInMemory.remove(getById(mealId));
    }
}
