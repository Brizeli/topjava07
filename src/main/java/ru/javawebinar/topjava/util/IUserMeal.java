package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.util.List;

/**
 * Created by Next on 03.06.2016.
 */
public interface IUserMeal {
    List<UserMealWithExceed> getAllUserMealsExceed();
    UserMeal getMeal(int mealId);
    boolean addMeal(UserMeal meal);
    boolean updateMeal(UserMeal meal);
    boolean delete(int id);
}
