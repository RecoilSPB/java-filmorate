MERGE INTO genre AS target
USING (VALUES
    (1, 'Комедия'),
    (2, 'Драма'),
    (3, 'Мультфильм'),
    (4, 'Триллер'),
    (5, 'Документальный'),
    (6, 'Боевик')
) AS source (id, name)
ON target.id = source.id
WHEN NOT MATCHED THEN
    INSERT (id, name) VALUES (source.id, source.name);

MERGE INTO mpa_rating AS target
    USING (VALUES (1, 'G'),
                  (2, 'PG'),
                  (3, 'PG-13'),
                  (4, 'R'),
                  (5, 'NC-17'))AS source (id, name)
    ON target.id = source.id
    WHEN NOT MATCHED THEN
        INSERT (id, name) VALUES (source.id, source.name);