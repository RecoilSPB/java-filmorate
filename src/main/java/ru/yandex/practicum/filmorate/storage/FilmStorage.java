package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface FilmStorage {
    Film add(Film film);

    Film update(Film updateFilm);

    Collection<Film> getAll();

    Film getById(long id);

    void delete(Long id);

    void addLike(Film film, User user);

    void removeLike(Long filmId, Long userId);
}
