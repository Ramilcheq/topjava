package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.IMealService;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String INSERT_OR_EDIT = "meal.jsp";
    private static final String MEAL_LIST = "meals.jsp";
    private IMealService mealService;

    @Override
    public void init() {
        mealService = new MealService();
        mealService.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        mealService.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        mealService.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        mealService.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        mealService.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        mealService.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        mealService.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        switch (action == null ? "" : action) {
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                delete(request, response);
                break;
            default:
                listMeal(request, response);
                break;
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("new meal form");
        RequestDispatcher view = request.getRequestDispatcher(INSERT_OR_EDIT);
        view.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("update meal form");
        Long mealId = Long.parseLong(request.getParameter("mealId"));
        Meal meal = mealService.getById(mealId);
        request.setAttribute("meal", meal);
        RequestDispatcher view = request.getRequestDispatcher(INSERT_OR_EDIT);
        view.forward(request, response);
    }

    private void listMeal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("meal list");
        request.setAttribute("meals", mealService.getAll());
        RequestDispatcher view = request.getRequestDispatcher(MEAL_LIST);
        view.forward(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("deleting meal");
        Long mealId = Long.parseLong(request.getParameter("mealId"));
        mealService.delete(mealId);
        response.sendRedirect("meals");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("editing/adding meal");
        request.setCharacterEncoding("UTF-8");
        mealService.saveMeal(request);
        response.sendRedirect("meals");
    }
}
