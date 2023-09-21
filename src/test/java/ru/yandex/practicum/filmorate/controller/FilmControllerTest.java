package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmControllerTest {
    FilmController filmController;
    UserController userController;

    @BeforeEach
    public void start() {
        InMemoryUserStorage userStorage = new InMemoryUserStorage();
        InMemoryFilmStorage filmStorage = new InMemoryFilmStorage();
        FilmService filmService = new FilmService(filmStorage,userStorage);
        UserService userService = new UserService(userStorage);
        filmController = new FilmController(filmService);
        userController = new UserController(userService);
    }


    @Test
    void createUnlimitReleasedFilm_shouldShowErrorMessage() {
        Film film = Film.builder()
                .id(1)
                .name("Кино")
                .description("ок")
                .releaseDate(LocalDate.now().minusYears(200))
                .duration(180)
                .build();
        ValidationException e = assertThrows(ValidationException.class, () -> filmController.create(film));

        assertEquals("Форма заполнена неправильно", e.getMessage());
    }

    @Test
    void updateUnlimitReleasedFilm_shouldShowErrorMessage() {
        Film film = Film.builder()
                .name("Кино")
                .description("Норм")
                .releaseDate(LocalDate.now().minusYears(43))
                .duration(240)
                .build();
        filmController.create(film);
        Film filmUpdate = Film.builder()
                .name("Кино")
                .description("Норм")
                .releaseDate(LocalDate.now().minusYears(230))
                .duration(193)
                .build();

        ValidationException e = assertThrows(ValidationException.class, () -> filmController.update(filmUpdate));

        assertEquals("Форма обновления заполнена неправильно", e.getMessage());
    }

    @Test
    void addLikeToFilm_ShouldAddLikeToFilm() {
        Film film = Film.builder()
                .id(1)
                .name("Кино")
                .description("Космос")
                .releaseDate(LocalDate.now().minusYears(43))
                .duration(240)
                .build();
        filmController.create(film);
        User user1 = new User(1, "freeeze1@ya.ru", "Денис", "", LocalDate.now().minusYears(35));
        userController.create(user1);
        filmController.addLike(1,1);

        assertEquals(1, film.getLikes().size());
    }
}