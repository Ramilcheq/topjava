package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface IMealDao {
    Meal create(Meal meal);

    Meal update(Meal meal);

    Meal getById(Long id);

    List<Meal> getAll();

    void delete(Long id);
}
