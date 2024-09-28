package ru.yandex.practicum.filmorate.storage.db.dml;

public class DmlFilm {
    public static final String FIND_ALL_QUERY = """
            SELECT f.*,
                   LISTAGG(DISTINCT fg.genre_id, ',') AS genres,
                   LISTAGG(DISTINCT fl.user_id, ',') AS likes
              FROM film f
              LEFT JOIN film_genre fg ON fg.film_id = f.id
              LEFT JOIN film_like fl ON fl.film_id = f.id
            GROUP BY f.id
            """;

    public static final String FIND_BY_ID_QUERY = """
            SELECT f.*,
                   LISTAGG(DISTINCT fg.genre_id, ',') AS genres,
                   LISTAGG(DISTINCT fl.user_id, ',') AS likes
              FROM film f
              LEFT JOIN film_genre fg ON fg.film_id = f.id
              LEFT JOIN film_like fl ON fl.film_id = f.id
             WHERE f.id = ?
            GROUP BY f.id
            """;

    public static final String INSERT_QUERY = """
            INSERT INTO film(name,
                             description,
                             release_date,
                             duration,
                             mpa_rating_id)
                     VALUES (?,
                             ?,
                             ?,
                             ?,
                             ?)
            """;

    public static final String UPDATE_QUERY = """
            UPDATE film
               SET name = ?,
                   description = ?,
                   release_date = ?,
                   duration = ?,
                   mpa_rating_id = ?
             WHERE id = ?
            """;

    public static final String DELETE_QUERY = """
            DELETE FROM film
             WHERE id = ?
            """;

    public static final String DELETE_GENRE_QUERY = """
            DELETE FROM film_genre fg
             WHERE fg.film_id = ?
            """;

    public static final String ADD_GENRE_QUERY = """
            INSERT INTO film_genre (film_id,
                                   genre_id)
                            VALUES(?,
                                   ?)
            """;

    public static final String DELETE_LIKE_QUERY = """
            DELETE FROM film_like fl
             WHERE fl.film_id = ?
               AND fl.user_id = ?
            """;

    public static final String ADD_LIKE_QUERY = """
            MERGE INTO film_like KEY(film_id,
                                     user_id)
                              VALUES(?,
                                     ?)
            """;
}
