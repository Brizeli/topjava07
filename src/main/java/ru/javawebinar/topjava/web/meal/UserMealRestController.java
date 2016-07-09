package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Created by Next on 08.07.2016.
 */
@Controller
@RequestMapping("meals")
public class UserMealRestController extends AbstractMealController {

    @RequestMapping(method = RequestMethod.GET)
    public String getAll(Model model) {
        model.addAttribute("mealList", super.getAll());
        return "mealList";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(HttpServletRequest request) {
        super.delete(getId(request));
        return "redirect:/meals";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(Model model, HttpServletRequest request) {
        model.addAttribute("meal", super.get(getId(request)));
        return "mealEdit";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("meal", new UserMeal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000));
        return "mealEdit";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String edit(HttpServletRequest request) {
        String id = request.getParameter("id");
        UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                                            LocalDateTime.parse(request.getParameter("dateTime")),
                                            request.getParameter("description"),
                                            Integer.valueOf(request.getParameter("calories")));
        if (userMeal.isNew()) super.create(userMeal);
        else super.update(userMeal, userMeal.getId());
        return "redirect:/meals";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String filter(Model model, HttpServletRequest request) {
        LocalDate startDate = TimeUtil.parseLocalDate(resetParam("startDate", request));
        LocalDate endDate = TimeUtil.parseLocalDate(resetParam("endDate", request));
        LocalTime startTime = TimeUtil.parseLocalTime(resetParam("startTime", request));
        LocalTime endTime = TimeUtil.parseLocalTime(resetParam("endTime", request));
        request.setAttribute("mealList", super.getBetween(startDate, startTime, endDate, endTime));
        return "mealList";
    }

    private String resetParam(String param, HttpServletRequest request) {
        String value = request.getParameter(param);
        request.setAttribute(param, value);
        return value;
    }

    private int getId(HttpServletRequest request) {
        String id = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(id);
    }
}
