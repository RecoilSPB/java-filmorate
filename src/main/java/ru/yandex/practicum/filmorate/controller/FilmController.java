package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public ResponseEntity<Film> createFilm(@Valid @RequestBody Film film) {
        Film createdFilm = filmService.add(film);
        return new ResponseEntity<>(createdFilm, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody(required = false) Film updatedFilm,
                                           BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            bindingResult.getFieldErrors()
                    .forEach(e ->
                            log.error("film update: field: {}, rejected value: {}", e.getField(), e.getRejectedValue()));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (updatedFilm == null) {
            log.error("film update: film is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (updatedFilm.getId() == null) {
            log.error("film update: film id is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Film film = filmService.update(updatedFilm);
        if (film == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(film, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() {
        List<Film> films = filmService.getAll();
        return new ResponseEntity<>(films, HttpStatus.OK);
    }
}