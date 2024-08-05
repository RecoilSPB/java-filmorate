package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService implements IBaseService<Film> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final List<Film> films = new ArrayList<>();
    private int id;

    @Override
    public Film add(Film film) {
        logger.debug("add Film...");
        film.setId(getNextId());
        films.add(film);
        logger.debug("add Film id: {}", film.getId());
        return film;
    }

    @Override
    public Film update(int id, Film dto) {
        for (Film film : films) {
            if (film.getId() == id) {
                film.setName(dto.getName());
                film.setDescription(dto.getDescription());
                film.setReleaseDate(dto.getReleaseDate());
                film.setDuration(dto.getDuration());
                return film;
            }
        }
        return null;
    }

    @Override
    public List<Film> getAll() {
        return films;
    }

    private int getNextId() {
        this.id++;
        return this.id;
    }
}
