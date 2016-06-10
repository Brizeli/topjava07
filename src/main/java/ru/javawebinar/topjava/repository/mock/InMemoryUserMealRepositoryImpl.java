package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private Comparator<UserMeal> userMealComparator = (meal1, meal2) -> meal2.getDateTime().compareTo(meal1.getDateTime());
    private Map<Integer, Map<Integer, UserMeal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        save(new UserMeal(LocalDateTime.of(2016, Month.JUNE, 1, 10, 0), "Завтрак", 500), 2);
        save(new UserMeal(LocalDateTime.of(2016, Month.JUNE, 1, 14, 0), "Обед", 1000), 2);
        save(new UserMeal(LocalDateTime.of(2016, Month.JUNE, 1, 20, 0), "Ужин", 500), 2);
        save(new UserMeal(LocalDateTime.of(2016, Month.JUNE, 2, 10, 0), "Завтрак", 500), 2);
        save(new UserMeal(LocalDateTime.of(2016, Month.JUNE, 2, 14, 0), "Обед", 1000), 2);
        save(new UserMeal(LocalDateTime.of(2016, Month.JUNE, 2, 20, 0), "Ужин", 600), 2);
        save(new UserMeal(LocalDateTime.of(2016, Month.JUNE, 3, 20, 0), "Админ ланч", 600), 1);
        save(new UserMeal(LocalDateTime.of(2016, Month.JUNE, 3, 20, 0), "Админ ужин", 600), 1);
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        Integer mealId = userMeal.getId();
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        } else if (get(mealId, userId) == null) return null;
        Map<Integer, UserMeal> userMeals = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        userMeals.put(mealId, userMeal);
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, UserMeal> userMeals = repository.get(userId);
        return userMeals != null && userMeals.remove(id) != null;
    }

    @Override
    public UserMeal get(int id, int userId) {
        Map<Integer, UserMeal> userMeals = repository.get(userId);
        return userMeals == null ? null : userMeals.get(id);
    }

    @Override
    public Collection<UserMeal> getAll(int userId) {
        return repository.get(userId).values().stream().sorted(userMealComparator).collect(Collectors.toList());
    }

    @Override
    public Collection<UserMeal> getBetween(LocalDateTime start, LocalDateTime end, int userId) {
        return getAll(userId).stream()
               .filter(meal -> TimeUtil.isBetween(meal.getDateTime(), start, end))
               .sorted(userMealComparator)
               .collect(Collectors.toList());
    }
}

