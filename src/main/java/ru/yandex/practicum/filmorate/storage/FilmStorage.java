package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Set;

public interface FilmStorage extends LikesStorage {

    Collection<Film> getAll();

    Film create(Film film);

    Film update(Film film);

    String delete(int id);

    Film getById(Integer id);

    void addGenre(int filmId, Set<Genre> genres);
}