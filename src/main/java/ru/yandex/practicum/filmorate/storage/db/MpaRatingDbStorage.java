package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.MpaRatingStorage;

import java.util.Collection;
import java.util.Optional;

import static ru.yandex.practicum.filmorate.storage.db.dml.DmlMpaRating.FIND_ALL_QUERY;
import static ru.yandex.practicum.filmorate.storage.db.dml.DmlMpaRating.FIND_BY_ID_QUERY;

@Repository
@Primary
public class MpaRatingDbStorage extends BaseDbStorage<MpaRating> implements MpaRatingStorage {

    public MpaRatingDbStorage(JdbcTemplate jdbc, RowMapper<MpaRating> mapper) {
        super(jdbc, mapper, MpaRating.class);
    }

    @Override
    public Collection<MpaRating> getAll() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public MpaRating getById(Long id) {
        Optional<MpaRating> mpa = findOne(FIND_BY_ID_QUERY, id);
        return mpa.orElse(null);
    }
}