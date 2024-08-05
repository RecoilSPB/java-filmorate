package ru.yandex.practicum.filmorate.service;

import java.util.List;

public interface IBaseService<T> {
    T add(T dto);

    T update(int id, T dto);

    List<T> getAll();
}