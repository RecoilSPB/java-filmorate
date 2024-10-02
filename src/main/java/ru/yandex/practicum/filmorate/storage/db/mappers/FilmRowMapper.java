package ru.yandex.practicum.filmorate.storage.db.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FilmRowMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getLong("id"));
        film.setName(rs.getString("name"));
        film.setDescription(rs.getString("description"));
        film.setReleaseDate(rs.getDate("release_date").toLocalDate());
        film.setDuration(rs.getLong("duration"));
        film.setMpa(generateMpaRating(rs.getLong("mpa_rating_id")));
        String likes = rs.getString("likes");
        film.setUserLikes(split(likes));
        film.setGenres(split(rs.getString("genres"))
                .stream()
                .map(this::generateGenre)
                .collect(Collectors.toList()));
        return film;
    }

    private MpaRating generateMpaRating(long mpaRatingId) {
        if (mpaRatingId == 0) {
            return null;
        }
        MpaRating mpaRating = new MpaRating();
        mpaRating.setId(mpaRatingId);
        return mpaRating;
    }

    private Set<Long> split(String str) {
        if (str == null || str.isBlank()) {
            return Collections.emptySet();
        }

        return Arrays.stream(str.split(","))
                .map(Long::parseLong)
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Genre generateGenre(Long id) {
        Genre genre = new Genre();
        genre.setId(id);
        return genre;
    }
}
