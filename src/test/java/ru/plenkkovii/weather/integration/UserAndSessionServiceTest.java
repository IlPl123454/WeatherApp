package ru.plenkkovii.weather.integration;


import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import ru.plenkkovii.weather.exception.WrongPasswordException;
import ru.plenkkovii.weather.repository.SessionRepository;
import ru.plenkkovii.weather.repository.UserRepository;
import ru.plenkkovii.weather.service.DataBaseSessionService;
import ru.plenkkovii.weather.service.SessionService;
import ru.plenkkovii.weather.service.UserService;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@Import({UserService.class, DataBaseSessionService.class})
public class UserAndSessionServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SessionRepository sessionRepository;

    private final String LOGIN = "login";
    private final String PASSWORD = "password";
    private final String WRONG_PASSWORD = "wrong_password";
    @Value("${session.duration}")
    private Duration sessionDuration;

    @Test
    void Login_ShouldLoginAndCreateSession() {
        userService.registerAndLogin(LOGIN, PASSWORD);

        UUID sessionUUID = userService.login(LOGIN, PASSWORD);

        assertTrue(sessionService.getSession(sessionUUID).isPresent());
    }

    @Test
    void Login_WhenUserIsNotRegistered_ShouldThrowException() {
        assertThrows(EntityNotFoundException.class, () -> userService.login(LOGIN, PASSWORD));
    }

    @Test
    void Login_WhenPasswordIsWrong_ShouldThrowException() {
        userService.registerAndLogin(LOGIN, PASSWORD);

        assertThrows(WrongPasswordException.class, () -> userService.login(LOGIN, WRONG_PASSWORD));
    }

    @Test
    void getSession_WhenSessionIsExpired_ShouldReturnOptionalEmpty() throws InterruptedException {
        UUID sessionUUID = userService.registerAndLogin(LOGIN, PASSWORD);

        assertTrue(sessionService.getSession(sessionUUID).isPresent());

        Thread.sleep(sessionDuration);

        assertTrue(sessionService.getSession(sessionUUID).isEmpty());
    }

    @Test
    public void RegisterAndLogin_ShouldRegisterAndCreateSession() {
        UUID sessionUUID = userService.registerAndLogin(LOGIN, PASSWORD);

        assertTrue(userRepository.findUserByLogin(LOGIN).isPresent());
        assertTrue(sessionService.getSession(sessionUUID).isPresent());
    }

    @Test
    public void RegisterAndLogin_WhenLoginExist_ShouldThrowException() {
        userService.registerAndLogin(LOGIN, PASSWORD);

        assertThrows(DataIntegrityViolationException.class,
                () -> userService.registerAndLogin(LOGIN, PASSWORD));
    }
}