package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.service.ModelService;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
public abstract class Controller<T extends Model> {
    private final ModelService<T> service;

    @Autowired
    protected Controller(ModelService<T> service) {
        this.service = service;
    }

    @GetMapping
    public Collection<T> getAll() {
        return service.getAll();
    }

    @PostMapping
    public T create(@Valid @RequestBody T obj) {
        service.create(obj);
        return obj;
    }

    @PutMapping
    public T update(@Valid @RequestBody T obj) {
        service.update(obj);
        return obj;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public T getById (@PathVariable Integer id) {
        return service.getById(id);
    }
}