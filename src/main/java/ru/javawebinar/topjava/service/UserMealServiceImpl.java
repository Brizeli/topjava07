package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class UserMealServiceImpl implements UserMealService {
    @Autowired
    private UserMealRepository repository;

    @Override
    public UserMeal get(int id, int userId) {
        return null;
    }

    @Override
    public UserMeal save(UserMeal meal, int userId) {
        return null;
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return null;
    }

    @Override
    public void update(UserMeal meal) {
    }

    @Override
    public List<UserMeal> getBetween(LocalDate startDate, LocalDate endDate, int userId) {
        return null;
    }

    @Override
    public void deleteAll(int userId) {
    }
}
