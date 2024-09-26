package ru.yandex.practicum.filmorate.model;

import lombok.Getter;

@Getter
public enum StatusFriend {
    UNCONFIRMED("Заявка в друзья"),
    CONFIRMED("Друг");

    private final String name;

    StatusFriend(String name) {
        this.name = name;
    }


}
