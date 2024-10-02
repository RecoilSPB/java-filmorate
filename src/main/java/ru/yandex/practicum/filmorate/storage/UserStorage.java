package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User add(User user);

    User update(User updatedUser);

    Collection<User> getAll();

    User getById(long id);

    User getByEmail(String email);

    void delete(Long id);

    void addFriend(User user, User friend);

    void deleteFriend(Long userId, Long friendId);
}
