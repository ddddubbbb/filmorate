package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController extends Controller<Film> {
    private static final LocalDate DATE_LIMIT = LocalDate.from(LocalDateTime.of(1895, 12, 28, 0, 0));

    @Override
    @Size(min = 1, max = 200)
    public boolean doValidate(Film film) {
        return !film.getReleaseDate().isBefore(DATE_LIMIT);
    }
}