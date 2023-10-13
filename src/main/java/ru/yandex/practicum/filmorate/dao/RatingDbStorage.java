package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RatingDbStorage {
    private final JdbcTemplate jdbcTemplate;

    public RatingDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Rating getRatingById(int ratingId) {
        String sqlQuery = "SELECT * FROM ratings WHERE rating_id = ?";
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sqlQuery, ratingId);
        if (srs.next()) {
            return new Rating(ratingId, srs.getString("rating_name"));
        }
        return null;
    }

    public List<Rating> getRatings() {
        List<Rating> ratings = new ArrayList<>();
        String sqlQuery = "SELECT * FROM ratings";
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sqlQuery);
        while (srs.next()) {
            ratings.add(new Rating(srs.getInt("rating_id"), srs.getString("rating_name")));
        }
        return ratings;
    }
}