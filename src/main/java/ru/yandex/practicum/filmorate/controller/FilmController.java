package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final List<Film> films = new ArrayList<>();

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        films.add(film);
        return film;
    }

    @PutMapping("/{id}")
    public Film updateFilm(@PathVariable int id, @RequestBody Film updatedFilm) {
        for (Film film : films) {
            if (film.getId() == id) {
                film.setName(updatedFilm.getName());
                film.setDescription(updatedFilm.getDescription());
                film.setReleaseDate(updatedFilm.getReleaseDate());
                film.setDuration(updatedFilm.getDuration());
                return film;
            }
        }
        return null;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return films;
    }
}