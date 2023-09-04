package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


import javax.validation.constraints.*;
import java.time.LocalDate;



@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class User extends Model {

    @PositiveOrZero
    private int id;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private String login;

    private String name;

    @PastOrPresent
    @NotNull
    private LocalDate birthday;
}