package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Model;

import java.util.Collection;

public interface Storage<T extends Model> {
    Collection<T> getAll();

    T create(T obj);

    T update(T obj);

    T delete(Integer id);

    T getById(Integer id);
}