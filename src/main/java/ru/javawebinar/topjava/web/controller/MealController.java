package ru.javawebinar.topjava.web.controller;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.IMealService;
import ru.javawebinar.topjava.service.impl.MealService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class MealController extends HttpServlet {
    private static final Logger log = getLogger(MealController.class);
    private static final String INSERT_OR_EDIT = "meal.jsp";
    private static final String MEAL_LIST = "meals.jsp";
    private final IMealService mealService;

    public MealController() {
        super();
        this.mealService = new MealService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("redirect to meals");
        String action = request.getParameter("action");

        if (action == null) {
            listMeal(request, response);
        } else {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    delete(request, response);
                    break;
                case "initDB":
                    initDB(request, response);
                    break;
                default:
                    listMeal(request, response);
                    break;
            }
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher(INSERT_OR_EDIT);
        view.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long mealId = Long.parseLong(request.getParameter("mealId"));
        Meal meal = mealService.getById(mealId);
        request.setAttribute("meal", meal);
        RequestDispatcher view = request.getRequestDispatcher(INSERT_OR_EDIT);
        view.forward(request, response);
    }

    private void listMeal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("meals", mealService.getAll());
        RequestDispatcher view = request.getRequestDispatcher(MEAL_LIST);
        view.forward(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long mealId = Long.parseLong(request.getParameter("mealId"));
        mealService.delete(mealId);
        response.sendRedirect("meals");
    }

    private void initDB(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mealService.initDB();
        request.setAttribute("meals", mealService.getAll());
        RequestDispatcher view = request.getRequestDispatcher(MEAL_LIST);
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        mealService.saveMeal(request);
        request.setAttribute("meals", mealService.getAll());
        response.sendRedirect("meals");
    }
}
