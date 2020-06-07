package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealDaoMemoryImpl implements IMealDao {
    private final AtomicLong daoId = new AtomicLong(0L);
    private final ConcurrentHashMap<Long, Meal> mealsInMemory = new ConcurrentHashMap<>();

    @Override
    public Meal create(Meal meal) {
        meal.setId(daoId.incrementAndGet());
        mealsInMemory.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal update(Meal meal) {
        Long mealId = meal.getId();
        if (mealId != null) {
            if (mealsInMemory.get(mealId) != null) {
                mealsInMemory.put(mealId, meal);
                return meal;
            }
        }
        return null;
    }

    @Override
    public Meal getById(Long id) {
        return mealsInMemory.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealsInMemory.values());
    }

    @Override
    public void delete(Long id) {
        mealsInMemory.remove(id);
    }
}
