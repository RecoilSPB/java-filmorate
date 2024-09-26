package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.StatusFriend;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.Date;
import java.util.Collection;
import java.util.Optional;

import static ru.yandex.practicum.filmorate.storage.db.dml.DmlUser.*;

@Repository
@Primary
public class UserDbStorage extends BaseDbStorage<User> implements UserStorage {

    public UserDbStorage(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper, User.class);
    }

    @Override
    public User add(User user) {
        long id = insert(
                INSERT_QUERY,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                Date.valueOf(user.getBirthday())
        );
        user.setId(id);
        return user;
    }

    @Override
    public User update(User updatedUser) {
        update(
                UPDATE_QUERY,
                updatedUser.getEmail(),
                updatedUser.getLogin(),
                updatedUser.getName(),
                Date.valueOf(updatedUser.getBirthday()),
                updatedUser.getId()
        );
        return updatedUser;
    }

    @Override
    public Collection<User> getAll() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public User getById(long id) {
        Optional<User> user = findOne(FIND_BY_ID_QUERY, id);
        return user.orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        Optional<User> user = findOne(FIND_BY_EMAIL_QUERY, email);
        return user.orElse(null);
    }

    @Override
    public void delete(Long id) {
        delete(DELETE_QUERY, id);
    }

    @Override
    public void addFriend(User user, User friend) {
        update(
                ADD_FRIEND_QUERY,
                user.getId(),
                friend.getId(),
                StatusFriend.UNCONFIRMED.name()
        );
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        delete(
                DELETE_FRIEND_QUERY,
                userId,
                friendId
        );
    }
}
