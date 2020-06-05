package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IMealService {
    void saveMeal(HttpServletRequest request);

    List<MealTo> getAll();

    void delete(Long mealId);

    Meal getById(Long mealId);

    void initDB();
}
