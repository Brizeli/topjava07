package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserMealServiceTest;

/**
 * Created by Next on 04.07.2016.
 */
@ActiveProfiles({Profiles.ACTIVE_DB,Profiles.JPA})
public class JpaUserMealServiceTest extends UserMealServiceTest {
}
