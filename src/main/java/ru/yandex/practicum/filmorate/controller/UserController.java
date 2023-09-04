package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends Controller<User> {

    @Override
    public void setEmptyUserName(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
            element.put(Math.toIntExact(user.getId()), user);
        }
    }
}