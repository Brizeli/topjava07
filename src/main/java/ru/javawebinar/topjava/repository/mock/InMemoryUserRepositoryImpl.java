package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        save(new User(1, "Ivanov", "ivanov@gmail.com", "admin", Role.ROLE_ADMIN));
        save(new User(2, "Petrov", "petrov@gmail.com", "petrov", Role.ROLE_USER));
        save(new User(3, "Sidorov", "sidorov@gmail.com", "sidorov", Role.ROLE_USER));
        save(new User(4, "Alexeev", "alexeev@gmail.com", "alexeev", Role.ROLE_USER));
    }

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if (user.isNew()) user.setId(counter.incrementAndGet());
        return repository.put(user.getId(), user);
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        return repository.values().stream().sorted((user1, user2) -> user1.getName().compareTo(user2.getName())).collect(Collectors.toList());
//        return Collections.emptyList();
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        return repository.values().stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }
}
