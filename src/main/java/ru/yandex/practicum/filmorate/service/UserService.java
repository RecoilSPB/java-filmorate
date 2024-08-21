package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class UserService implements IBaseService<User> {

    protected HashMap<Long, User> users;
    private long id;

    public UserService() {
        users = new HashMap<>();
        id = 0;
    }

    @Override
    public User add(User user) {
        log.debug("add User...");
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        Long nextId = getNextId();
        user.setId(nextId);
        users.put(nextId, user);
        log.debug("add User id: {}", user.getId());
        return user;
    }

    @Override
    public User update(User updatedUser) {
        if (updatedUser == null || updatedUser.getId() == null) {
            String msg = "Invalid user data for update.";
            throw new IllegalArgumentException(msg);
        }
        if (users.isEmpty()) {
            String msg = "Invalid user data for update.";
            throw new IllegalArgumentException(msg);
        }

        if (updatedUser.getName() == null || updatedUser.getName().isEmpty()) {
            updatedUser.setName(updatedUser.getLogin());
        }

        if (users.containsKey(updatedUser.getId())) {
            users.put(updatedUser.getId(), updatedUser);
            return users.get(updatedUser.getId());
        }
        throw new UserNotFoundException("User not found with id: " + updatedUser.getId());
    }

    @Override
    public List<User> getAll() {
        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found.");
        }
        return new ArrayList<>(users.values());
    }

    private Long getNextId() {
        this.id++;
        return this.id;
    }
}
