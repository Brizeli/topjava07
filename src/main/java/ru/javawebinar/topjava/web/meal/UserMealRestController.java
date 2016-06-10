package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;

import java.time.LocalDate;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserMealService service;

    public UserMeal get(int id){
        int userId = LoggedUser.id();
        LOG.info("get meal {} for user {}",id,userId);
        return service.get(id,userId);
    }
    public void delete(int id){
        int userId = LoggedUser.id();
        LOG.info("delete meal {} for user {}",id,userId);
        service.delete(id,userId);
    }
    public List<UserMeal> getAll(){
        int userId = LoggedUser.id();
        LOG.info("get all for user {}",userId);
        return service.getAll(userId);
    }
    public void deleteAll(){
        int userId = LoggedUser.id();
        LOG.info("delete all for user {}",userId);
        service.deleteAll(userId);
    }
    public void update(UserMeal meal){
        int userId = LoggedUser.id();
        LOG.info("update {} for user {}",meal,userId);
        service.update(meal);
    }
    public UserMeal create(UserMeal meal){
        int userId = LoggedUser.id();
        LOG.info("create {} for user {}",meal,userId);
        return service.save(meal,userId);
    }
    public List<UserMeal> getBetween(LocalDate startDate, LocalDate endDate){
        int userId = LoggedUser.id();
        LOG.info("get between {} and {} for user {}",startDate,endDate,userId);
        return service.getBetween(startDate,endDate,userId);
    }
}
