package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Model;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class Controller<T extends Model> {
    protected final Map<Integer, T> element = new HashMap<>();
    private int id = 0;

    @GetMapping
    public Collection<T> getAll() {
        log.info("Количество объектов " + element.size());
        return element.values();
    }

    @PostMapping
    public T create(@Valid @RequestBody T obj) {
        validate(obj, "Форма объекта заполнена неправильно");
        obj.setId(setId());
        setEmptyUserName(obj);
        element.put(obj.getId(), obj);
        log.info("Объект добавлен " + obj);
        return obj;
    }

    @PutMapping
    public T update(@Valid @RequestBody T obj) {
        validate(obj, "Форма обновления объекта была заполнена неправильно");
        if (!element.containsKey(obj.getId())) {
            throw new ValidationException("Объекта нет в списке");
        }
        setEmptyUserName(obj);
        element.put(obj.getId(), obj);
        log.info("Объекта успешно добавлен: " + obj);
        return obj;
    }

    private int setId() {
        id += 1;
        return id;
    }

    private void validate(T obj, String message) {
        if (!doValidate(obj)) {
            throw new ValidationException(message);
        }
    }

    protected boolean doValidate(T obj) {
        return true;
    }

    protected void setEmptyUserName(T obj) {
    }
}