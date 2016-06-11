package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private ConfigurableApplicationContext springContext;
    private UserMealRestController mealController;
//    private UserMealRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        springContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealController = springContext.getBean(UserMealRestController.class);
//        repository = new InMemoryUserMealRepositoryImpl();
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            String id = request.getParameter("id");
            UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                                                    LocalDateTime.parse(request.getParameter("dateTime")),
                                                    request.getParameter("description"),
                                                    Integer.valueOf(request.getParameter("calories")));
            LOG.info(userMeal.isNew() ? "Create {}" : "Update {}", userMeal);
            mealController.create(userMeal);
            response.sendRedirect("meals");
        } else {
            LocalDate startDate = TimeUtil.parseLocalDate(request.getParameter("startDate"), TimeUtil.MIN_DATE);
            LocalTime startTime = TimeUtil.parseLocalTime(request.getParameter("startTime"), LocalTime.MIN) ;
            LocalDate endDate = TimeUtil.parseLocalDate(request.getParameter("endDate"), TimeUtil.MAX_DATE);
            LocalTime endTime = TimeUtil.parseLocalTime(request.getParameter("endTime"), LocalTime.MAX);
            request.setAttribute("mealList", mealController.getBetween(startDate, startTime, endDate, endTime));
            request.getRequestDispatcher("/mealList.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("mealList", mealController.getAll());
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            mealController.delete(id);
            response.sendRedirect("meals");
        } else if (action.equals("create") || action.equals("update")) {
            final UserMeal meal = action.equals("create") ?
                                          new UserMeal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000) :
                                          mealController.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
