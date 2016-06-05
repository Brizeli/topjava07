package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Next on 03.06.2016.
 */
public class UserMealMemoryImpl implements IUserMeal {
    private static int id = 1;
    private final Map<Integer, UserMeal> mealMap = new HashMap<>();

    public UserMealMemoryImpl() {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        for (UserMeal meal : mealList) {
            meal.setId(id);
            mealMap.put(id++, meal);
        }
    }

    @Override
    public synchronized boolean addMeal(UserMeal meal) {
        if (meal.getId() != 0 && mealMap.get(meal.getId()) != null) return false;
        meal.setId(id);
        mealMap.put(id++, meal);
        return true;
    }

    @Override
    public synchronized boolean updateMeal(UserMeal meal) {
        if (meal.getId() == 0 || mealMap.get(meal.getId()) == null) return false;
        mealMap.put(meal.getId(), meal);
        return true;
    }

    @Override
    public synchronized boolean delete(int id) {
        return mealMap.remove(id) != null;
    }

    @Override
    public synchronized List<UserMealWithExceed> getAllUserMealsExceed() {
        List<UserMeal> mealList = mealMap.values().stream().collect(Collectors.toList());
        return UserMealsUtil.getFilteredWithExceeded(mealList, LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
    }

    @Override
    public synchronized UserMeal getMeal(int mealId) {
        return mealMap.get(mealId);
    }
}
