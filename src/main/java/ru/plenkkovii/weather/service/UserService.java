package ru.plenkkovii.weather.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.plenkkovii.weather.model.User;
import ru.plenkkovii.weather.repository.UserRepository;
import ru.plenkkovii.weather.utils.BCryptUtil;

@AllArgsConstructor

@Service
public class UserService {

    private final UserRepository userRepository;


    public User login(String username, String password) {
        User user = findByLogin(username);
        validateLogInPasswordEquals(password, user.getPassword());

        return user;
    }

    public User save(String login, String password1, String password2) {
        validateLoginNotTaken(login);
        validateRegisterPasswordEquals(password1, password2);

        User user = new User();
        user.setLogin(login);
        user.setPassword(BCryptUtil.hash(password1)); // вот тут наверняка можно лучше создать и вернуть объект

        return userRepository.save(user);
    }

    //TODO не кидать общие исключения (сделать детальней)
    private void validateLoginNotTaken(String login) {
        if (userRepository.findUserByLogin(login).isPresent()) {
            throw new IllegalArgumentException("Login is already taken");
        }
    }

    private void validateRegisterPasswordEquals(String password1, String password2) {
        if (!password1.equals(password2)) {
            throw new IllegalArgumentException("Passwords don't match");
        }
    }

    private User findByLogin(String login) {
        return userRepository.findUserByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Password or login is wrong"));
    }

    private void validateLogInPasswordEquals(String password1, String password2) {
        if (!BCryptUtil.checkPassword(password1, password2)) {
            throw new IllegalArgumentException("Password or login is wrong");
        }
    }
}
