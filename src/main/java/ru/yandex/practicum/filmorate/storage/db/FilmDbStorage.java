package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.storage.db.dml.DmlFilm.*;

@Repository
@Primary
public class FilmDbStorage extends BaseDbStorage<Film> implements FilmStorage {

    public FilmDbStorage(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper, Film.class);
    }

    @Override
    public Film add(Film film) {
        long id = insert(
                INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getMpa().getId()
        );
        setGenres(id, getGenresId(film.getGenres()));
        film.setId(id);
        return film;
    }

    @Override
    public Film update(Film updateFilm) {
        update(
                UPDATE_QUERY,
                updateFilm.getName(),
                updateFilm.getDescription(),
                Date.valueOf(updateFilm.getReleaseDate()),
                updateFilm.getDuration(),
                updateFilm.getMpa().getId(),
                updateFilm.getId()
        );
        setGenres(updateFilm.getId(), getGenresId(updateFilm.getGenres()));
        return updateFilm;
    }

    @Override
    public Collection<Film> getAll() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public Film getById(long id) {
        Optional<Film> film = findOne(FIND_BY_ID_QUERY, id);
        return film.orElse(null);
    }

    @Override
    public void delete(Long id) {
        delete(DELETE_QUERY, id);
    }

    @Override
    public void addLike(Film film, User user) {
        update(
                ADD_LIKE_QUERY,
                film.getId(),
                user.getId()
        );
    }

    @Override
    public void removeLike(Long filmId, Long userId) {
        delete(
                DELETE_LIKE_QUERY,
                filmId,
                userId
        );
    }

    private void setGenres(Long filmId, List<Long> genres) {
        delete(DELETE_GENRE_QUERY, filmId);
        if (genres != null && !genres.isEmpty()) {
            genres.forEach(genreId -> update(
                    ADD_GENRE_QUERY,
                    filmId,
                    genreId
            ));
        }
    }

    private List<Long> getGenresId(List<Genre> genres) {
        if (genres == null) {
            return null;
        }
        return genres.stream()
                .map(Genre::getId)
                .collect(Collectors.toList());
    }
}
