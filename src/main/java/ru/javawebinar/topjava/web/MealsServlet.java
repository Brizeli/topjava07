package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.IUserMeal;
import ru.javawebinar.topjava.util.UserMealMemoryImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Next on 03.06.2016.
 */
public class MealsServlet extends HttpServlet {
    private static final Logger LOG = getLogger(UserServlet.class);
    private IUserMeal meals = new UserMealMemoryImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LOG.debug("request: " + request.getParameterMap());
        String id = request.getParameter("id");
        String datetime = request.getParameter("datetime");
        LocalDateTime mealDateTime = LocalDateTime.parse(datetime);
        String description = request.getParameter("desc");
        int calories = Integer.parseInt(request.getParameter("cal"));
        UserMeal meal = new UserMeal(mealDateTime, description, calories);
        String result;
        if (id.isEmpty()) {
            result = meals.addMeal(meal) ? "Meal added" : "Error adding meal";
            request.setAttribute("addupd", "Add");
        }
        else {
            meal.setId(Integer.parseInt(id));
            result = meals.updateMeal(meal) ? "Meal updated" : "Error updating meal";
            request.setAttribute("meal", meal);
            request.setAttribute("addupd", "Update");
        }
        request.setAttribute("result", result);
        LOG.debug("Redirect to: /addMeal.jsp, result: Meal Id "+meal.getId()+": "+result);
        RequestDispatcher view = request.getRequestDispatcher("/addMeal.jsp");
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("Get request: " + request.getParameterMap());
        String action = request.getParameter("action");
        String forward = "/index.html";
        if (action == null || action.isEmpty()) {
            forward = "/mealsList.jsp";
            request.setAttribute("mealsList", meals.getAllUserMealsExceed());
        } else
            switch (action) {
                case "edit":
                    UserMeal meal = meals.getMeal(Integer.parseInt(request.getParameter("id")));
                    request.setAttribute("meal", meal);
                    request.setAttribute("addupd", "Update");
                    forward = "/addMeal.jsp";
                    break;
                case "addmeal":
                    request.setAttribute("addupd", "Add");
                    forward = "/addMeal.jsp";
                    break;
                case "delete":
                    boolean result = meals.delete(Integer.parseInt(request.getParameter("id")));
                    forward = "/mealsList.jsp";
                    request.setAttribute("result", result ? "Meal deleted" : "Error deleting meal");
                    request.setAttribute("mealsList", meals.getAllUserMealsExceed());
            }
        LOG.debug("Forward to: " + forward + ", action: " + action);
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }
}
