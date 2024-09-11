package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.Comparator;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserService userStorage;

    public Film add(Film film) {
        log.debug("add Film id: {}", film.getId());
        return filmStorage.add(film);
    }

    public Film update(Film updateFilm) {
        log.debug("update Film...");
        if (updateFilm == null || updateFilm.getId() == null) {
            String msg = "Invalid film data for update.";
            throw new IllegalArgumentException(msg);
        }
        return filmStorage.update(updateFilm);

    }

    public Collection<Film> getAll() {
        return filmStorage.getAll();
    }

    public Film getById(long id) {
        Film film = filmStorage.getById(id);
        if (film == null) {
            throw new NotFoundException(String.format("Пользователь с ид %s не найден", id));
        }
        return film;
    }

    public void addLike(long filmId, long userId) {
        log.info("FilmService: addLike film id = {}, user id = {}", filmId, userId);
        Film film = filmStorage.getById(filmId);
        User user = userStorage.getById(userId);
        if (film == null) {
            throw new NotFoundException(String.format("Фильм с ид %s не найден", filmId));
        }
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с ид %s не найден", userId));
        }
        film.getUserLikes().add(userId);
    }

    public void removeLike(Long filmId, Long userId) {
        log.info("FilmService: removeLike film id = {}, user id = {}", filmId, userId);
        Film film = filmStorage.getById(filmId);
        User user = userStorage.getById(userId);
        if (film == null) {
            throw new NotFoundException(String.format("Фильм с ид %s не найден", filmId));
        }
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с ид %s не найден", userId));
        }
        film.getUserLikes().remove(userId);
    }

    public Collection<Film> getTopLikedFilms(long limit) {
        return filmStorage.getAll()
                .stream()
                .sorted(Comparator.comparing(film -> film.getUserLikes().size(), Comparator.reverseOrder()))
                .limit(limit)
                .toList();

    }
}
