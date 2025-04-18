package ru.plenkkovii.weather.service;

import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.plenkkovii.weather.exception.LoginAlreadyExistException;
import ru.plenkkovii.weather.exception.WrongPasswordException;
import ru.plenkkovii.weather.model.User;
import ru.plenkkovii.weather.repository.UserRepository;

import java.util.UUID;

@AllArgsConstructor

@Service
public class UserService {

    private final UserRepository userRepository;

    private final SessionService sessionService;

    public Cookie login(String username, String password) {
        User user = findByLogin(username);

        validateLogInPasswordEquals(password, user.getPassword());

        UUID uuid = sessionService.saveSession(user);

        Cookie sessionUuid = new Cookie("SESSION_UUID", uuid.toString());
        sessionUuid.setPath("/");

        return sessionUuid;
    }


    public Cookie registerAndLogin(String login, String password1) {
        User user = User.builder()
                .login(login)
                .password(BCrypt.hashpw(password1, BCrypt.gensalt()))
                .build();

        userRepository.save(user);

        UUID uuid = sessionService.saveSession(user);

        Cookie sessionUuid = new Cookie("SESSION_UUID", uuid.toString());
        sessionUuid.setPath("/"); // код дублируется, возможно надо куда-то выенсти

        return sessionUuid;
    }


    private User findByLogin(String login) {
        return userRepository.findUserByLogin(login)
                .orElseThrow(() -> new LoginAlreadyExistException("Вы ввели неверный логин или пароль"));
    }

    private void validateLogInPasswordEquals(String password1, String password2) {
        if (!BCrypt.checkpw(password1, password2)) {
            throw new WrongPasswordException("Вы ввели неверный логин или пароль");
        }
    }
}
