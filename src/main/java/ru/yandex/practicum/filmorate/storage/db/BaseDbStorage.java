package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import ru.yandex.practicum.filmorate.exception.InternalServerException;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BaseDbStorage<T> {
    protected final JdbcTemplate jdbc;
    protected final RowMapper<T> mapper;
    @SuppressWarnings("unused")
    private final Class<T> entityType;

    protected Optional<T> findOne(String query, Object... params) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Query must not be null or empty");
        }
        try {
            T result = jdbc.queryForObject(query, mapper, params);
            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException ignored) {
            return Optional.empty();
        }
    }

    protected List<T> findMany(String query, Object... params) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Query must not be null or empty");
        }
        return jdbc.query(query, mapper, params);
    }

    public boolean delete(String query, long id) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Query must not be null or empty");
        }
        int rowsDeleted = jdbc.update(query, id);
        return rowsDeleted > 0;
    }

    public boolean delete(String query, long id, long id2) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Query must not be null or empty");
        }
        int rowsDeleted = jdbc.update(query, id, id2);
        return rowsDeleted > 0;
    }

    protected Long insert(String query, Object... params) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Query must not be null or empty");
        }
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            for (int idx = 0; idx < params.length; idx++) {
                ps.setObject(idx + 1, params[idx]);
            }
            System.out.println("ps = " + ps);
            return ps;
        }, keyHolder);
        Long id = keyHolder.getKeyAs(Long.class);
        if (id != null) {
            return id;
        } else {
            throw new InternalServerException("Не удалось сохранить данные");
        }
    }

    protected void update(String query, Object... params) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Query must not be null or empty");
        }
        int rowsUpdated = jdbc.update(query, params);
        if (rowsUpdated == 0) {
            throw new InternalServerException("Не удалось обновить данные");
        }
    }
}