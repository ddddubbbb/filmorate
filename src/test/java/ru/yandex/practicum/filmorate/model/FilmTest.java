package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FilmTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createFilmWithEmptyName_shouldShowErrorMessage() {
        Film film = Film.builder()
                .name(null)
                .description("Trash")
                .releaseDate(LocalDate.now().minusYears(14))
                .duration(-180)
                .build();
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);

        assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    void createFilmWithTooLongDescription_shouldShowErrorMessage() {
        String description = "блаблалал".repeat(200);
        Film film = Film.builder().name("Кино").description(description)
                .releaseDate(LocalDate.now().minusYears(13)).duration(280).build();
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);

        assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    void createFilmWithMinusDuration_shouldShowErrorMessage() {
        Film film = Film.builder()
                .name("КИНО")
                .description("Блаблаблаблабла")
                .releaseDate(LocalDate.now().minusYears(20))
                .duration(-180)
                .build();
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);

        assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    void updateFilmWithEmptyName_shouldShowErrorMessage() {
        Film film = Film.builder()
                .name("Кино")
                .description("Блаблбла")
                .releaseDate(LocalDate.now().minusYears(14))
                .duration(180)
                .build();
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);
        Film film2 = Film.builder()
                .name(null)
                .description("Блаблбла")
                .releaseDate(LocalDate.now().minusYears(14))
                .duration(180)
                .build();
        HttpEntity<Film> entity = new HttpEntity<>(film2);
        ResponseEntity<Film> response2 = restTemplate.exchange("/films", HttpMethod.PUT, entity, Film.class);

        assertEquals("400 BAD_REQUEST", response2.getStatusCode().toString());

        System.out.println(response2.getBody());
        System.out.println("hello");
    }

    @Test
    void updateFilmWithTooLongDescription_shouldShowErrorMessage() {
        Film film = Film.builder()
                .name("Кино")
                .description("Блаблбла")
                .releaseDate(LocalDate.now().minusYears(14))
                .duration(180)
                .build();
        restTemplate.postForLocation("/films", film);
        String description = "Blablabla".repeat(200);
        Film film2 = Film.builder()
                .name("Movie")
                .description(description)
                .releaseDate(LocalDate.now().minusYears(13))
                .duration(180)
                .build();
        HttpEntity<Film> entity = new HttpEntity<>(film2);
        ResponseEntity<Film> response2 = restTemplate.exchange("/films", HttpMethod.PUT, entity, Film.class);

        assertEquals("400 BAD_REQUEST", response2.getStatusCode().toString());
    }

    @Test
    void updateFilmWithMinusDuration_shouldShowErrorMessage() {
        Film film = Film.builder()
                .name("Кино")
                .description("Блаблбла")
                .releaseDate(LocalDate.now().minusYears(14))
                .duration(180)
                .build();
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);
        Film film2 = Film.builder()
                .name("Кино")
                .description("Блаблбла")
                .releaseDate(LocalDate.now().minusYears(14))
                .duration(-180)
                .build();
        HttpEntity<Film> entity = new HttpEntity<>(film2);
        ResponseEntity<Film> response2 = restTemplate.exchange("/films", HttpMethod.PUT, entity, Film.class);

        assertEquals("400 BAD_REQUEST", response2.getStatusCode().toString());
    }
}