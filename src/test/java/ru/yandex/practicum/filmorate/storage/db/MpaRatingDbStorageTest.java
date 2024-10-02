package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.db.mappers.MpaRatingRowMapper;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@Import({MpaRatingDbStorage.class, MpaRatingRowMapper.class})
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("DbMpaStorage")
class MpaRatingDbStorageTest {
    private final MpaRatingDbStorage mpaStorage;

    private static Collection<MpaRating> getAllTestMpa() {
        return List.of(
                getMpa(1L, "G"),
                getMpa(2L, "PG"),
                getMpa(3L, "PG-13"),
                getMpa(4L, "R"),
                getMpa(5L, "NC-17")
        );
    }

    private static MpaRating getMpa(Long id, String name) {
        MpaRating mpa = new MpaRating();
        mpa.setId(id);
        mpa.setName(name);
        return mpa;
    }

    @Test
    void shouldReturnFiveMpa() {
        Collection<MpaRating> mpaList = mpaStorage.getAll();
        assertEquals(getAllTestMpa().size(), mpaList.size(), "Неверное количество записей рейтинга");
        assertEquals(getAllTestMpa(), mpaList, "Не совпадают наборы записей рейтинга");
    }

    @Test
    void shouldReturnPG13Mpa() {
        Long mpaId = 3L;
        MpaRating dbMpa = mpaStorage.getById(mpaId);
        assertEquals("PG-13", dbMpa.getName(), String.format("Неверное название рейтинга для ид = %d", mpaId));
    }
}