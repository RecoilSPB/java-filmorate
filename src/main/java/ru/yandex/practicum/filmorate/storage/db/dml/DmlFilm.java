package ru.yandex.practicum.filmorate.storage.db.dml;

public class DmlFilm {
    public static final String FIND_ALL_QUERY = """
            SELECT f.*,
                   (SELECT Listagg(fg.genre_id, ',')
                      FROM film_genre fg
                     WHERE fg.film_id = f.id) as genres,
                   (SELECT Listagg(fl.user_id, ',')
                      FROM film_like fl
                     WHERE fl.film_id = f.id) as likes
              FROM film f
            """;

    public static final String FIND_BY_ID_QUERY = """
            SELECT f.*,
                   (SELECT Listagg(fg.genre_id, ',')
                      FROM film_genre fg
                     WHERE fg.film_id = f.id) as genres,
                   (SELECT Listagg(fl.user_id, ',')
                      FROM film_like fl
                     WHERE fl.film_id = f.id) as likes
            FROM film f WHERE f.id = ?
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
