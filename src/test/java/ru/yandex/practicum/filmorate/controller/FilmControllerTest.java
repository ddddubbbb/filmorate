package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmControllerTest {
    FilmController filmController;

    @BeforeEach
    public void start() {
        filmController = new FilmController();
    }

    @Test
    void createUnlimitReleasedFilm_shouldShowErrorMessage() {
        Film film = new Film(666, "Film", "Trash", LocalDate.now().minusYears(200), 180);

        ValidationException e = assertThrows(ValidationException.class, () -> filmController.create(film));

        assertEquals("Форма объекта заполнена неправильно", e.getMessage());
    }

    @Test
    void updateUnlimitReleasedFilm_shouldShowErrorMessage() {
        Film film = new Film(118, "Film", "Drama", LocalDate.now().minusYears(43), 240);
        filmController.create(film);
        Film filmUpdate = new Film(122, "Film", "Sci-Fi", LocalDate.now().minusYears(230), 193);

        ValidationException e = assertThrows(ValidationException.class, () -> filmController.update(filmUpdate));

        assertEquals("Форма обновления объекта была заполнена неправильно", e.getMessage());
    }
}