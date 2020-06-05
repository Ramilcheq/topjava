package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface IMealDao {
    void create(Meal meal);

    void update(Meal meal);

    Meal getById(Long id);

    List<Meal> getAll();

    void delete(Long mealId);

    void initDB();
}
