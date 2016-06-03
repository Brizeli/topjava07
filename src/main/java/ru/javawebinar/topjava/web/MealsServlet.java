package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.util.IUserMeal;
import ru.javawebinar.topjava.util.UserMealMemoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Next on 03.06.2016.
 */
public class MealsServlet extends HttpServlet {
    private static final Logger LOG = getLogger(UserServlet.class);
    private IUserMeal meals = new UserMealMemoryImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("mealsList", meals.getAllUserMealsExceed());
        request.getRequestDispatcher("/mealsList.jsp").forward(request, response);
    }
}
