package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

@Slf4j
@Component
public class InMemoryUserStorage extends ModelStorage<User> implements UserStorage {
}