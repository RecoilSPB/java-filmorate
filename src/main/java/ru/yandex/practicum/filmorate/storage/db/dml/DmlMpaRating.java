package ru.yandex.practicum.filmorate.storage.db.dml;

public class DmlMpaRating {
    public static final String FIND_ALL_QUERY = """
            SELECT *
              FROM mpa_rating
            """;

    public static final String FIND_BY_ID_QUERY = """
            SELECT *
              FROM mpa_rating
             WHERE id = ?
            """;
}
