package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.Collection;

@Slf4j
@Service
public abstract class ModelService<T extends Model> {
    protected final Storage<T> storage;

    protected ModelService(Storage<T> storage) {
        this.storage = storage;
    }

    public Collection<T> getAll() {
        log.info("Список всех объектов: " + storage.getAll().size());
        return storage.getAll();
    }

    public T create(T obj) {
        validate(obj, "Форма заполнена неправильно");
        preSave(obj);
        log.info("Объект добавлен: " + obj);
        return storage.create(obj);
    }

    public T update(T obj) {
        validate(obj, "Форма обновления заполнена неправильно");
        preSave(obj);
        log.info("Объект обновлен: " + obj);
        return storage.update(obj);
    }

    public T delete(Integer id) {
        log.info("Удален объект с id: {}", id);
        return storage.delete(id);
    }

    public T getById(Integer id) {
        log.info("Запрошен объект с id: " + id);
        return storage.getById(id);
    }

    protected void validate(T obj, String message) {
        if (!doValidate(obj)) {
            throw new ValidationException(message);
        }
    }

    protected boolean doValidate(T obj) {
        return true;
    }

    protected void preSave(T obj) {
    }
}