package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
public class User {
    private int id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String login;

    private String name;

    @PastOrPresent
    @NotNull
    private LocalDate birthday;

    @JsonIgnore
    private final Set<Integer> friendIds = new HashSet<>();

    public void addFriend(Integer id) {
        friendIds.add(id);
    }

    public void deleteFriend(Integer id) {
        friendIds.remove(id);
    }
}