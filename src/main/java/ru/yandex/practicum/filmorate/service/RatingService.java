package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.dao.RatingDbStorage;

import java.util.List;

@Service
public class RatingService {
    private final RatingDbStorage ratingDbStorage;

    public RatingService(RatingDbStorage ratingDbStorage) {
        this.ratingDbStorage = ratingDbStorage;
    }

    public Rating getRatingById(int id) {
        Rating mpa = ratingDbStorage.getRatingById(id);
        if (mpa == null) {
            throw new NotFoundException("Рейтинг не найден");
        }
        return mpa;
    }

    public List<Rating> getRatings() {
        return ratingDbStorage.getRatings();
    }
}