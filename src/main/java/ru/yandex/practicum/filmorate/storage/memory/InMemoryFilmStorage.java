package ru.yandex.practicum.filmorate.storage.memory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    protected Map<Long, Film> films = new HashMap<>();
    private long id = 0;



    @Override
    public Film add(Film film) {
        Long nextId = getNextId();
        film.setId(nextId);
        films.put(nextId, film);
        return film;
    }

    @Override
    public Film update(Film updateFilm) {
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
        throw new NotFoundException("Film not found with id: " + updateFilm.getId());
    }

    @Override
    public Collection<Film> getAll() {
        if (films.isEmpty()) {
            throw new NotFoundException("No users found.");
        }
        return films.values();
    }

    @Override
    public Film getById(long id) {
        return films.get(id);
    }

    private Long getNextId() {
        this.id++;
        return this.id;
    }
}
