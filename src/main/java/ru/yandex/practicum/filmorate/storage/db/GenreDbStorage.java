package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.Collection;
import java.util.Optional;

import static ru.yandex.practicum.filmorate.storage.db.dml.DmlGenre.FIND_ALL_QUERY;
import static ru.yandex.practicum.filmorate.storage.db.dml.DmlGenre.FIND_BY_ID_QUERY;

@Repository
@Primary
public class GenreDbStorage extends BaseDbStorage<Genre> implements GenreStorage {


    public GenreDbStorage(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper, Genre.class);
    }

    @Override
    public Collection<Genre> getAll() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public Genre getById(Long id) {
        Optional<Genre> genre = findOne(FIND_BY_ID_QUERY, id);
        return genre.orElse(null);
    }
}
