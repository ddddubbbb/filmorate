package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class FilmService {
    private final UserStorage userStorage;
    private final FilmStorage filmStorage;
    private static final LocalDate LIMIT_DATE = LocalDate.from(LocalDateTime.of(1895, 12, 28, 0, 0));
    private static final int LIMIT_LENGTH_OF_DESCRIPTION = 200;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.userStorage = userStorage;
        this.filmStorage = filmStorage;
    }

    public Collection<Film> getAll() {
        log.info("Список всех фильмов: " + filmStorage.getAll().size());
        return filmStorage.getAll();
    }

    public Film create(Film film) {
        validate(film, "Форма 'фильм' заполнена неверно");
        Film result = filmStorage.create(film);
        log.info("Фильм добавлен: " + film);
        return result;
    }

    public Film update(Film film) {
        validate(film, "Форма 'обновление фильма' заполнена неверно");
        Film result = filmStorage.update(film);
        log.info("Фильм обновлен: " + film);
        return result;
    }

    public void delete(int filmId) {
        if (getById(filmId) == null) {
            throw new NotFoundException("Фильм с ID = " + filmId + " не найден");
        }
        log.info("Удаление фильма с id: {}", filmId);
        filmStorage.delete(filmId);
    }

    public Film getById(Integer id) {
        log.info("Запрос пользователя с ID = " + id);
        return filmStorage.getById(id);
    }

    public void addLike(Integer filmId, Integer userId) {
        Film film = filmStorage.getById(filmId);
        if (film != null) {
            if (userStorage.getById(userId) != null) {
                filmStorage.addLike(filmId, userId);
                log.info("Like добавлен");
            } else {
                throw new NotFoundException("Пользователь с ID = " + userId + " не найден");
            }
        } else {
            throw new NotFoundException("Фильм с ID = " + filmId + " не найден");
        }
    }

    public void removeLike(Integer filmId, Integer userId) {
        Film film = filmStorage.getById(filmId);
        if (film != null) {
            if (userStorage.getById(userId) != null) {
                filmStorage.removeLike(filmId, userId);
                log.info("Like удален");
            } else {
                throw new NotFoundException("Пользователь с ID = " + userId + " не найден");
            }
        } else {
            throw new NotFoundException("Фильм с ID = " + filmId + " не найден");
        }
    }

    public List<Film> getPopular(Integer count) {
        List<Film> result = new ArrayList<>(filmStorage.getPopular(count));
        log.info("Запрошен лист популярных фильмов");
        return result;
    }

    protected void validate(Film film, String message) {
        if (film.getDescription().length() > LIMIT_LENGTH_OF_DESCRIPTION || film.getReleaseDate().isBefore(LIMIT_DATE)) {
            log.debug(message);
            throw new ValidationException(message);
        }
    }
}