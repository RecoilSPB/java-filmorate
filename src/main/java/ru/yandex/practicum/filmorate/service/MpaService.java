package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.MpaRatingStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class MpaService {
    private final MpaRatingStorage mpaRatingStorage;

    public Collection<MpaRating> getAllMpa() {
        return mpaRatingStorage.getAll();
    }

    public MpaRating getMpaById(Long id) {
        MpaRating mpa = mpaRatingStorage.getById(id);
        if (mpa == null) {
            throw new NotFoundException(String.format("Рейтинг с ид %s не найден", id));
        }
        return mpa;
    }
}
