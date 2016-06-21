package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);
    public static final Map<Integer, List<UserMeal>> MEAL_MAP = new ConcurrentHashMap<>();

    static {
        MEAL_MAP.put(USER_ID, Arrays.asList(
                new UserMeal(START_SEQ + 2, LocalDateTime.of(2016, Month.JUNE, 10, 10, 0), "Завтрак", 500),
                new UserMeal(START_SEQ + 3, LocalDateTime.of(2016, Month.JUNE, 10, 13, 0), "Обед", 1000),
                new UserMeal(START_SEQ + 4, LocalDateTime.of(2016, Month.JUNE, 10, 20, 0), "Ужин", 500),
                new UserMeal(START_SEQ + 5, LocalDateTime.of(2016, Month.JUNE, 11, 10, 0), "Завтрак", 500),
                new UserMeal(START_SEQ + 6, LocalDateTime.of(2016, Month.JUNE, 11, 13, 0), "Обед", 1000),
                new UserMeal(START_SEQ + 7, LocalDateTime.of(2016, Month.JUNE, 11, 20, 0), "Ужин", 510)));
        MEAL_MAP.put(ADMIN_ID, Arrays.asList(
                new UserMeal(START_SEQ + 8, LocalDateTime.of(2016, Month.JUNE, 12, 10, 0), "Админ ланч", 510),
                new UserMeal(START_SEQ + 9, LocalDateTime.of(2016, Month.JUNE, 12, 18, 0), "Админ ужин", 1500)));
    }
}
