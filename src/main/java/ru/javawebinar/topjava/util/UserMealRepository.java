package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.util.Collection;
import java.util.List;

/**
 * Created by Next on 03.06.2016.
 */
public interface UserMealRepository {
    Collection<UserMeal> getAll();
    UserMeal get(int mealId);
    boolean add(UserMeal meal);
    boolean update(UserMeal meal);
    boolean delete(int id);
}
