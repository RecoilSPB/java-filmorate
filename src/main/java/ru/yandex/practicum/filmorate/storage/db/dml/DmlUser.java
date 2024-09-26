package ru.yandex.practicum.filmorate.storage.db.dml;

public class DmlUser {
    public static final String FIND_ALL_QUERY = """
            SELECT u.*,
                   (SELECT Listagg(uf.friend_id, ',')
                      FROM user_friend uf
                     WHERE uf.user_id = u.id) as friends
              FROM usr u
            """;

    public static final String FIND_BY_ID_QUERY = """
            SELECT u.*,
                   (SELECT Listagg(uf.friend_id, ',')
                      FROM user_friend uf
                     WHERE uf.user_id = u.id) as friends
              FROM usr u
             WHERE u.id = ?
            """;

    public static final String FIND_BY_EMAIL_QUERY = """
            SELECT u.*,
                   (SELECT Listagg(uf.friend_id, ',')
                      FROM user_friend uf
                     WHERE uf.user_id = u.id) as friends
              FROM usr u
             WHERE LOWER(u.email) = LOWER(?)
            """;

    public static final String INSERT_QUERY = """
            INSERT INTO usr(email,
                              login,
                              name,
                              birth_day)
                      VALUES (?,
                              ?,
                              ?,
                              ?)
            """;

    public static final String UPDATE_QUERY = """
            UPDATE usr
               SET email = ?,
                   login = ?,
                   name = ?,
                   birth_day = ?
             WHERE id = ?""";

    public static final String DELETE_QUERY = """
            DELETE FROM usr
             WHERE id = ?
            """;

    public static final String ADD_FRIEND_QUERY = """
            MERGE INTO user_friend KEY(user_id,
                                       friend_id,
                                       status
                                       )
                                values(?,
                                       ?,
                                       ?)
            """;

    public static final String DELETE_FRIEND_QUERY = """
            DELETE FROM user_friend
             WHERE user_id = ?
               AND friend_id =?
            """;
}
