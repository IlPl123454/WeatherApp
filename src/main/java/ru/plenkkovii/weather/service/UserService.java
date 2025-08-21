package ru.plenkkovii.weather.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.dao.DataIntegrityViolationException;
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

    public UUID login(String username, String password) {
        User user = findByLogin(username);

        validateLogInPasswordEquals(password, user.getPassword());

        return sessionService.createSession(user);
    }

    public UUID registerAndLogin(String login, String password1) {
        User user = User.builder()
                .login(login)
                .password(BCrypt.hashpw(password1, BCrypt.gensalt()))
                .build();

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException && e.getMessage().contains("unique_name")) {
                throw new LoginAlreadyExistException("Пользователь с таким логином уже существет");
            }
            throw e;

        }

        return sessionService.createSession(user);
    }

    public void logout(String sessionId) {
        sessionService.deleteSession(UUID.fromString(sessionId));
    }


    private User findByLogin(String login) {
        return userRepository.findUserByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("Вы ввели неверный логин или пароль"));
    }

    private void validateLogInPasswordEquals(String password1, String password2) {
        if (!BCrypt.checkpw(password1, password2)) {
            throw new WrongPasswordException("Вы ввели неверный логин или пароль");
        }
    }
}
