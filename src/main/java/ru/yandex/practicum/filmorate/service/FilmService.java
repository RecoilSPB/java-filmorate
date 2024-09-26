package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.DublicateException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.WrongArgumentException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.MpaRatingStorage;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserService userStorage;
    private final MpaRatingStorage mpaRatingStorage;
    private final GenreStorage genreStorage;

    public Film add(Film film) {
        log.debug("add Film id: {}", film.getId());
        return filmStorage.add(completeFilmData(film));
    }

    private Film completeFilmData(Film film) {
        if (film.getMpa() != null) {
            Long mpaId = film.getMpa().getId();
            film.setMpa(checkMpaRatingExist(mpaId));
        }
        film.setGenres(checkGenresExist(film.getGenres()));
        return film;
    }

    private List<Genre> checkGenresExist(List<Genre> genres) {
        if (genres == null || genres.isEmpty()) {
            return Collections.emptyList();
        }
        return genres.stream()
                .map(genre -> checkGenreExist(genre.getId()))
                .collect(Collectors.toList());
    }

    private Genre checkGenreExist(Long genreId) {
        Genre genre = genreStorage.getById(genreId);
        if (genre == null) {
            throw new WrongArgumentException(String.format("Жанр с ид %s не найден", genreId));
        }
        return genre;
    }


    private MpaRating checkMpaRatingExist(Long mpaId) {
        if (mpaId == null) {
            return null;
        }
        MpaRating mpaRating = mpaRatingStorage.getById(mpaId);
        if (mpaRating == null) {
            throw new WrongArgumentException(String.format("Рейтинг с ид %s не найден", mpaId));
        }
        return mpaRating;
    }

    public Film update(Film updateFilm) {
        log.debug("update Film...");
        if (updateFilm == null || updateFilm.getId() == null) {
            String msg = "Invalid film data for update.";
            throw new IllegalArgumentException(msg);
        }
        return filmStorage.update(completeFilmData(updateFilm));

    }

    public Collection<Film> getAll() {
        return filmStorage.getAll().stream()
                .peek(this::completeFilmData)
                .toList();
    }

    public Film getById(long id) {
        Film film = filmStorage.getById(id);
        if (film == null) {
            throw new NotFoundException(String.format("Пользователь с ид %s не найден", id));
        }
        return completeFilmData(film);
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
        if (film.getUserLikes().contains(user.getId())) {
            throw new DublicateException("Фильм уже отмечен данным пользователем");
        }
        filmStorage.addLike(film, user);
    }

    public void removeLike(Long filmId, Long userId) {
        log.info("FilmService: removeLike film id = {}, user id = {}", filmId, userId);
        checkFilmExist(filmId);
        checkUserExist(userId);
        filmStorage.removeLike(filmId, userId);
    }

    private void checkUserExist(Long userId) {
        User user = userStorage.getById(userId);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с ид %s не найден", userId));
        }
    }

    private void checkFilmExist(Long filmId) {
        Film film = filmStorage.getById(filmId);
        if (film == null) {
            throw new NotFoundException(String.format("Фильм с ид %s не найден", filmId));
        }
    }

    public Collection<Film> getTopLikedFilms(long limit) {
        return filmStorage.getAll()
                .stream()
                .sorted(Comparator.comparing(film -> film.getUserLikes().size(), Comparator.reverseOrder()))
                .limit(limit)
                .toList();

    }
}
