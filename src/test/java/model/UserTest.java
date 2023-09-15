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
class UserTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createUserWithBadEmail_shouldShowErrorMessage() {
        User user = new User(10, "фрииизер1@.ру", "Denis", "Денис", LocalDate.now().minusYears(39));
        ResponseEntity<User> response = restTemplate.postForEntity("/users", user, User.class);

        assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    void createUserWithEmptyLogin_shouldShowErrorMessage() {
        User user = new User(10, "freeeze1@ya.ru", " ", "Денис", LocalDate.now().minusYears(39));
        ResponseEntity<User> response = restTemplate.postForEntity("/users", user, User.class);

        assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    void createUserWithEmptyName_shouldShowErrorMessage() {
        int id = 1;
        String login = "Denis";
        User user = new User(id, "freeeze1@ya.ru", login, null, LocalDate.now().minusYears(39));
        ResponseEntity<User> response = restTemplate.postForEntity("/users", user, User.class);

        assertEquals(login, response.getBody().getName());
    }

    @Test
    void createFutureBirthUser_shouldShowErrorMessage() {
        User user = new User(10, "freeeze1@ya.ru", "Denis", "Денис", LocalDate.now().plusYears(39));
        ResponseEntity<User> response = restTemplate.postForEntity("/users", user, User.class);

        assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    void updateUserToEmptyEmail_shouldShowErrorMessage() {
        User usr = new User(1, "freeeze1@ya.ru", "Denis", "Денис", LocalDate.now().minusYears(39));
        ResponseEntity<User> response = restTemplate.postForEntity("/users", usr, User.class);

        User user2 = new User(1, null, "Denis", "Денис", LocalDate.now().minusYears(39));
        HttpEntity<User> entity = new HttpEntity<>(user2);
        ResponseEntity<User> response2 = restTemplate.exchange("/users", HttpMethod.PUT, entity, User.class);

        assertEquals("400 BAD_REQUEST", response2.getStatusCode().toString());
    }

    @Test
    void updateUserToEmptyLogin_shouldShowErrorMessage() {
        User user = new User(10, "freeeze1@ya.ru", "Denis", "Денис", LocalDate.now().minusYears(39));
        ResponseEntity<User> response = restTemplate.postForEntity("/users", user, User.class);
        User user1 = new User(10, "freeeze1@ya.ru", " ", "Денис", LocalDate.now().minusYears(39));
        HttpEntity<User> entity = new HttpEntity<>(user1);
        ResponseEntity<User> response2 = restTemplate.exchange("/users", HttpMethod.PUT, entity, User.class);

        assertEquals("400 BAD_REQUEST", response2.getStatusCode().toString());
    }

    @Test
    void updateFutureBirthUser_shouldShowErrorMessage() {
        User user = new User(10, "freeeze1@ya.ru", "Denis", "Денис", LocalDate.now().minusYears(39));
        ResponseEntity<User> response = restTemplate.postForEntity("/users", user, User.class);
        User user1 = new User(10, "freeeze1@ya.ru", "Denis", "Денис", LocalDate.now().plusYears(39));
        HttpEntity<User> entity = new HttpEntity<>(user1);
        ResponseEntity<User> response2 = restTemplate.exchange("/users", HttpMethod.PUT, entity, User.class);

        assertEquals("400 BAD_REQUEST", response2.getStatusCode().toString());
    }
}