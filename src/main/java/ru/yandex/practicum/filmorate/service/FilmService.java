package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class FilmService implements IBaseService<Film> {

    protected HashMap<Long, Film> films;
    private long id;

    public FilmService() {
        this.films = new HashMap<>();
        this.id = 0;
    }

    @Override
    public Film add(Film film) {
        log.debug("add Film...");
        Long nextId = getNextId();
        film.setId(nextId);
        films.put(nextId, film);
        log.debug("add Film id: {}", film.getId());
        return film;
    }

    @Override
    public Film update(Film updateFilm) {
        log.debug("update Film...");
        if (updateFilm == null || updateFilm.getId() == null) {
            String msg = "Invalid film data for update.";
            throw new IllegalArgumentException(msg);
        }
        if (films.isEmpty()) {
            String msg = "Invalid film data for update.";
            throw new IllegalArgumentException(msg);
        }

        if (films.containsKey(updateFilm.getId())) {
            films.put(updateFilm.getId(), updateFilm);
            return films.get(updateFilm.getId());
        }
        throw new FilmNotFoundException("Film not found with id: " + updateFilm.getId());
    }

    @Override
    public List<Film> getAll() {
        if (films.isEmpty()) {
            throw new FilmNotFoundException("No users found.");
        }
        return new ArrayList<>(films.values());
    }

    private Long getNextId() {
        this.id++;
        return this.id;
    }
}
