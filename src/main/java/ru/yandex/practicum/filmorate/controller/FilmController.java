package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.ErrorResponse;
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
    public ResponseEntity<?> createFilm(@Valid @RequestBody Film film) {
        Film createdFilm = filmService.add(film);
        return new ResponseEntity<>(createdFilm, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateFilm(@Valid @RequestBody Film updatedFilm) {
        if (updatedFilm == null) {
            String msg = "film update: film is null";
            log.error(msg);
            ErrorResponse error = new ErrorResponse(null, null, msg);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        if (updatedFilm.getId() == null) {
            String msg = "film update: film id is null";
            log.error(msg);
            ErrorResponse error = new ErrorResponse("id", updatedFilm, msg);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        Film film = filmService.update(updatedFilm);
        if (film == null) {
            String msg = "film update: film not found";
            log.error(msg);
            ErrorResponse error = new ErrorResponse("id", updatedFilm, msg);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(film, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllFilms() {
        List<Film> films = filmService.getAll();
        if (films.isEmpty()) {
            String msg = "film not found";
            log.error(msg);
            ErrorResponse error = new ErrorResponse(null, null, msg);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(films, HttpStatus.OK);
    }
}