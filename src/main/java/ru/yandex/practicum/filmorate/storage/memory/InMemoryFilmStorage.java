package ru.yandex.practicum.filmorate.storage.memory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    protected Map<Long, Film> films;
    private long id;

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
        throw new FilmNotFoundException("Film not found with id: " + updateFilm.getId());
    }

    @Override
    public Collection<Film> getAll() {
        if (films.isEmpty()) {
            throw new FilmNotFoundException("No users found.");
        }
        return films.values();
    }

    private Long getNextId() {
        this.id++;
        return this.id;
    }
}
