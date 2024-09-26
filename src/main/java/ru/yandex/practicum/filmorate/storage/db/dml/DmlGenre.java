package ru.yandex.practicum.filmorate.storage.db.dml;

public class DmlGenre {
    public static final String FIND_ALL_QUERY = """
            SELECT *
              FROM genre
            """;

    public static final String FIND_BY_ID_QUERY = """
            SELECT *
              FROM genre
             WHERE id = ?
            """;
}
