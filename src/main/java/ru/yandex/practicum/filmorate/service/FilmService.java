package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;

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
            throw new FilmNotFoundException(String.format("Пользователь с ид %s не найден", id));
        }
        return film;
    }
}
