package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService extends ModelService<User> {
    @Autowired
    public UserService(UserStorage storage) {
        super(storage);
    }

    @Override
    protected void preSave(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    public void addFriend(Integer userId, Integer friendId) {
        if (storage.getById(userId) == null || storage.getById(friendId) == null) {
            throw new NotFoundException("Объекта нет в списке");
        }
        getById(userId).addFriend(friendId);
        getById(friendId).addFriend(userId);
        log.info("Друг добавлен");
    }

    public void removeFriend(Integer userId, Integer friendId) {
        if (storage.getById(userId) == null || storage.getById(friendId) == null) {
            throw new NotFoundException("Объекта нет в списке");
        }
        getById(userId).deleteFriend(friendId);
        getById(friendId).deleteFriend(userId);
        log.info("Друг удален");
    }

    public List<User> getFriends(Integer userId) {
        if (storage.getById(userId) == null) {
            throw new NotFoundException("Объекта нет в списке");
        }
        User user = getById(userId);
        log.info("Друзья пользователя с id " + userId);
        return user.getFriends()
                .stream()
                .map(storage::getById)
                .sorted(Comparator.comparingInt(User::getId))
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Integer user1Id, Integer user2Id) {
        if (storage.getById(user1Id) == null ||
                storage.getById(user2Id) == null) {
            throw new NotFoundException("Объекта нет в списке");
        }
        List<User> userSet1 = getFriends(user1Id);
        List<User> userSet2 = getFriends(user2Id);
        log.info("Общие друзья пользователя с id " + user1Id + " и id " + user2Id);
        return userSet1
                .stream()
                .filter(userSet2::contains)
                .sorted(Comparator.comparingInt(User::getId))
                .collect(Collectors.toList());
    }
}