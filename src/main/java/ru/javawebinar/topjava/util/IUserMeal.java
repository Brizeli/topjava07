package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.util.List;

/**
 * Created by Next on 03.06.2016.
 */
public interface IUserMeal {
    List<UserMealWithExceed> getAllUserMealsExceed();
}
