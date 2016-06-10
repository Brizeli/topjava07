package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface UserMealService {
    UserMeal get(int id, int userId);
    UserMeal save(UserMeal meal, int userId);

    void delete(int id, int userId) throws NotFoundException;

    List<UserMeal> getAll(int userId);

    void update(UserMeal meal);

    List<UserMeal> getBetween(LocalDate startDate, LocalDate endDate, int userId);

    void deleteAll(int userId);
}
