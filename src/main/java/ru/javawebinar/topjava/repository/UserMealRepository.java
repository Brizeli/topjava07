package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public interface UserMealRepository {
    UserMeal save(UserMeal userMeal, int iserId);

    boolean delete(int id, int userId);

    UserMeal get(int id, int userId);

    List<UserMeal> getAll(int userId);

    List<UserMeal> getBetween(LocalDateTime start, LocalDateTime end, int userId);

    boolean deleteAll(int userId);
}
