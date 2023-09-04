package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {
    UserController userController;

    @BeforeEach
    public void start() {
        userController = new UserController();
    }

    @Test
    void createEmptyName_shouldAddNameAsLogin() {
        int id = 1;
        String login = "Denis";
        User user = new User(id, "freeeze1@yandex.ru", login, null, LocalDate.now().minusYears(39));
        userController.create(user);

        assertEquals(login, userController.element.get(id).getName());
    }

    @Test
    void updateUserNameToEmpty_shouldSetNameToLogin() {
        User user1 = new User(1, "freeeze1@yandex.ru", "Denis", "", LocalDate.now().minusYears(39));
        userController.create(user1);
        int id = 1;
        String login = "Denis";
        User user = new User(id, "freeeze1@yandex.ru", login, null, LocalDate.now().minusYears(39));
        userController.update(user);
        System.out.println(userController.element);

        assertEquals(login, userController.element.get(id).getName());
    }
}