package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IBaseService<User> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final List<User> users = new ArrayList<>();
    private int id;


    @Override
    public User add(User user) {
        logger.debug("add User...");
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        user.setId(getNextId());
        users.add(user);
        logger.debug("add User id: {}", user.getId());
        return user;
    }

    @Override
    public User update(int id, User dto) {
        for (User user : users) {
            if (user.getId() == id) {
                user.setEmail(dto.getEmail());
                user.setLogin(dto.getLogin());
                user.setName(dto.getName().isEmpty() ? dto.getLogin() : dto.getName());
                user.setBirthday(dto.getBirthday());
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    private int getNextId(){
        this.id++;
        return this.id;
    }
}
