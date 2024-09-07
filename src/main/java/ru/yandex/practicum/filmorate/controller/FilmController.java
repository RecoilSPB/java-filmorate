package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос на добавление фильма");
        return filmService.add(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film updatedFilm) {
        log.info("Получен запрос на обновление фильма c id: {}", updatedFilm.getId());
        return filmService.update(updatedFilm);
    }

    @PutMapping("/{id}/like/{userId}")
    private void addLike(@PathVariable("id") long filmId,
                         @PathVariable("userId") long userId) {
        log.info("Получен запрос на добавление лайка");
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable("id") long filmId, @PathVariable("userId") long userId) {
        log.info("Получен запрос на удаление лайка");
        filmService.removeLike(filmId, userId);
    }

    @GetMapping
    public Collection<Film> getAllFilms() {
        log.info("Получен запрос на получение всех фильмов");
        return filmService.getAll();
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable("id") long filmId) {
        log.info("Получен запрос на получение данных о фильме");
        return filmService.getById(filmId);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(
            @RequestParam(value = "count", defaultValue = "10", required = false) Long limit
    ) {
        log.info("Получен запрос на получения списка популярных фильмов");
        return filmService.getTopLikedFilms(limit);
    }
}