package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.MealTestData.MEAL_MAP;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by Next on 21.06.2016.
 */
@ContextConfiguration({
                          "classpath:spring/spring-app.xml",
                          "classpath:spring/spring-db.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {
    @Autowired
    protected UserMealService service;
    @Autowired
    private DbPopulator dbPopulator;
    private static final Comparator<UserMeal> USER_MEAL_COMPARATOR = (meal1, meal2) -> meal2.getDateTime().compareTo(meal1.getDateTime());

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        UserMeal actual = service.get(100002, USER_ID);
        UserMeal expected = MEAL_MAP.get(USER_ID).get(0);
        MATCHER.assertEquals(expected, actual);
    }

    @Test(expected = NotFoundException.class)
    public void testGetWrongUser() throws Exception {
        service.get(100002, ADMIN_ID);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(100002, USER_ID);
        Collection<UserMeal> expected = service.getAll(USER_ID);
        Collection<UserMeal> actual = MEAL_MAP.get(USER_ID).subList(1, 6)
                                          .stream().sorted(USER_MEAL_COMPARATOR)
                                          .collect(Collectors.toList());
        MATCHER.assertCollectionEquals(expected, actual);
    }

    @Test(expected = NotFoundException.class)
    public void testDeletWrongUser() throws Exception {
        service.delete(100002, ADMIN_ID);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        LocalDateTime startDateTime = LocalDateTime.of(2016, Month.JUNE, 10, 10, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2016, Month.JUNE, 11, 10, 0);
        Collection<UserMeal> expected = service.getBetweenDateTimes(startDateTime, endDateTime, USER_ID);
        Collection<UserMeal> actual = MEAL_MAP.get(USER_ID)
                                          .stream().filter(meal -> TimeUtil.isBetween(meal.getDateTime(), startDateTime, endDateTime))
                                          .sorted(USER_MEAL_COMPARATOR).collect(Collectors.toList());
        MATCHER.assertCollectionEquals(expected, actual);
    }

    @Test
    public void getAll() throws Exception {
        Collection<UserMeal> expected = service.getAll(USER_ID);
        Collection<UserMeal> actual = MEAL_MAP.get(USER_ID).stream().sorted(USER_MEAL_COMPARATOR).collect(Collectors.toList());
        MATCHER.assertCollectionEquals(expected,actual);
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal actual = new UserMeal(100002, LocalDateTime.now(), "New food", 300);
        UserMeal expected = service.update(actual, USER_ID);
        MATCHER.assertEquals(expected, actual);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateWrongUser() throws Exception {
        service.update(new UserMeal(100002, LocalDateTime.now(), "New food", 300), ADMIN_ID);
    }

    @Test
    public void testSave() throws Exception {
        UserMeal newMeal = new UserMeal(LocalDateTime.now(), "New food", 300);
        service.save(newMeal,ADMIN_ID);
        List<UserMeal> adminMeals = new ArrayList<>(MEAL_MAP.get(ADMIN_ID));
        adminMeals.add(newMeal);
        Collection<UserMeal> expected = service.getAll(ADMIN_ID);
        Collection<UserMeal> actual = adminMeals.stream().sorted(USER_MEAL_COMPARATOR).collect(Collectors.toList());
        MATCHER.assertCollectionEquals(expected,actual);
    }
}