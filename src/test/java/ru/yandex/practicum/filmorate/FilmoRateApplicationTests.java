package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.dao.RatingDbStorage;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class FilmoRateApplicationTests {
    private final FilmDbStorage filmDbStorage;
    private final RatingDbStorage ratingDbStorage;
    private final UserDbStorage userDbStorage;

    @Test
    void getAllFilms_shouldConfirmThatTwoFilmsWasAddedAtList() {
        Film film1 = Film.builder().id(1).name("1984")
                .description("жизненно")
                .releaseDate(LocalDate.now().minusYears(40))
                .duration(180).genres(new HashSet<>()).rating(ratingDbStorage.getRatingById(5)).build();
        Film film2 = Film.builder().id(1).name("Мы")
                .description("про нас")
                .releaseDate(LocalDate.now().minusYears(40))
                .duration(180).genres(new HashSet<>()).rating(ratingDbStorage.getRatingById(1)).build();
        filmDbStorage.create(film1);
        filmDbStorage.create(film2);
        Collection<Film> films = filmDbStorage.getAll();

        assertThat(films).hasSize(2);
    }

    @Test
    void createFilm_shouldConfirmThatFilmIdExists() {
        Film film = Film.builder().id(1).name("1984")
                .description("жизненно")
                .releaseDate(LocalDate.now().minusYears(40))
                .duration(180).genres(new HashSet<>()).rating(ratingDbStorage.getRatingById(5)).build();
        filmDbStorage.create(film);
        Film filmOptional = filmDbStorage.getById(1);

        assertEquals(filmOptional.getId(), 1);
    }

    @Test
    void getFilmById_shouldConfirmThatFilmIdExists() {
        Film film = Film.builder().id(1).name("1984")
                .description("жизненно")
                .releaseDate(LocalDate.now().minusYears(39))
                .duration(180).genres(new HashSet<>()).rating(ratingDbStorage.getRatingById(5)).build();
        filmDbStorage.create(film);

        assertEquals(filmDbStorage.getById(1).getId(), film.getId());
    }

    @Test
    public void getAllUsers_shouldConfirmThatTwoUsersWasAddedAtList() {
        User user1 = User.builder()
                .id(1)
                .name("Денис")
                .login("Denis")
                .email("freeeze1@ya.ru")
                .birthday(LocalDate.now().minusYears(39))
                .build();
        User user2 = User.builder()
                .id(2)
                .name("Виктор")
                .login("Viktor")
                .email("Viktor@ya.ru")
                .birthday(LocalDate.now().minusYears(39))
                .build();
        userDbStorage.create(user1);
        userDbStorage.create(user2);
        Collection<User> users = userDbStorage.getAll();

        assertThat(users).contains(user1);
        assertThat(users).contains(user2);
    }

    @Test
    public void createUser_shouldConfirmThatUserIdExists() {
        User user = User.builder()
                .id(1)
                .name("Денис")
                .login("Denis")
                .email("freeeze1@ya.ru")
                .birthday(LocalDate.now().minusYears(39))
                .build();
        userDbStorage.create(user);
        User userOptional = userDbStorage.getById(1);

        assertEquals(userOptional.getId(), 1);
    }

    @Test
    public void getUserById_shouldConfirmThatUserNameExists() {
        User user = User.builder()
                .id(1)
                .name("Денис")
                .login("Denis")
                .email("freeeze1@ya.ru")
                .birthday(LocalDate.now().minusYears(39))
                .build();
        userDbStorage.create(user);
        User userOptional = userDbStorage.getById(1);

        assertEquals(userOptional.getName(), "Денис");
    }
}