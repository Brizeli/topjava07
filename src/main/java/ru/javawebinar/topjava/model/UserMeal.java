package ru.javawebinar.topjava.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
@Entity
@NamedQueries({
      @NamedQuery(name = UserMeal.DELETE, query = "DELETE FROM UserMeal m WHERE m.id=:id and m.user.id=:userId"),
      @NamedQuery(name = UserMeal.ALL_SORTED, query = "select m from UserMeal m where m.user.id=:userId order by m.dateTime desc"),
      @NamedQuery(name = UserMeal.GET, query = "select m from UserMeal m where m.id=:id and m.user.id=:userId"),
      @NamedQuery(name = UserMeal.GET_BETWEEN, query = "select m from UserMeal m where m.user.id=:userId and m.dateTime between :startDate and :endDate order by m.dateTime")
})
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "meals_unique_user_datetime_idx")})
public class UserMeal extends BaseEntity {

    public static final String DELETE = "Meal.delete";
    public static final String ALL_SORTED = "Meal.getAllSorted";
    public static final String GET = "Meal.get";
    public static final String GET_BETWEEN = "Meal.getBetween";

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotNull
    private String description;

    @Column(name = "calories", nullable = false)
    @NotNull
    protected int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    public UserMeal() {
    }

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                   "id=" + id +
                   ", dateTime=" + dateTime +
                   ", description='" + description + '\'' +
                   ", calories=" + calories +
                   '}';
    }
}
