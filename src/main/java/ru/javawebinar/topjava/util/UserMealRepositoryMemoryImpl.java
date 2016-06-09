package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by Next on 03.06.2016.
 */
public class UserMealRepositoryMemoryImpl implements UserMealRepository {
    private static int id = 1;
    private final Map<Integer, UserMeal> mealMap = new ConcurrentHashMap<>();

    {
        for (UserMeal meal : UserMealsUtil.mealList) {
            add(meal);
        }
    }

    @Override
    public boolean add(UserMeal meal) {
        if (meal.getId() != 0 && mealMap.get(meal.getId()) != null) return false;
        meal.setId(id);
        mealMap.put(id++, meal);
        return true;
    }

    @Override
    public boolean update(UserMeal meal) {
        if (meal.getId() == 0 || mealMap.get(meal.getId()) == null) return false;
        mealMap.put(meal.getId(), meal);
        return true;
    }

    @Override
    public boolean delete(int id) {
        return mealMap.remove(id) != null;
    }

    @Override
    public Collection<UserMeal> getAll() {
        return mealMap.values();
    }

    @Override
    public UserMeal get(int mealId) {
        return mealMap.get(mealId);
    }
}
