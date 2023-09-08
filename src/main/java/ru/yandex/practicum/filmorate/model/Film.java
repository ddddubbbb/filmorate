package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class Film extends Model {

    @PositiveOrZero
    private int id;

    @NotBlank
    private String name;

    @NotBlank
    @Size(max = 200)
    private String description;

    @NotNull  @Past
    private LocalDate releaseDate;

    @Positive
    private long duration;
}