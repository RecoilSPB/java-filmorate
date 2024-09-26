package ru.yandex.practicum.filmorate.storage.memory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    protected Map<Long, User> users = new HashMap<>();
    private long id = 0;

    @Override
    public User add(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        Long nextId = getNextId();
        user.setId(nextId);
        users.put(nextId, user);
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
        throw new NotFoundException("User not found with id: " + updatedUser.getId());
    }

    @Override
    public Collection<User> getAll() {
        if (users.isEmpty()) {
            throw new NotFoundException("No users found.");
        }
        return users.values();
    }

    private Long getNextId() {
        this.id++;
        return this.id;
    }

    @Override
    public User getById(long id) {
        return users.get(id);
    }

    @Override
    public User getByEmail(String email) {
        return users.values()
                .stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(Long id) {
        users.remove(id);
    }

    @Override
    public void addFriend(User user, User friend) {
        user.getFriends().add(friend.getId());
        friend.getFriends().add(user.getId());
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        users.get(userId).getFriends().remove(friendId);
        users.get(friendId).getFriends().remove(userId);
    }
}
