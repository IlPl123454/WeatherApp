package ru.plenkkovii.weather.service;

import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.plenkkovii.weather.model.User;
import ru.plenkkovii.weather.repository.UserRepository;

@AllArgsConstructor

@Service
public class UserService {

    private final UserRepository userRepository;

    public User login(String username, String password) {
        User user = findByLogin(username);
        validateLogInPasswordEquals(password, user.getPassword());

        return user;
    }

    public User save(String login, String password1) {
        validateLoginNotTaken(login);
        User user = User.builder()  // тут наверное надо использовать dto объект
                .login(login)
                .password(BCrypt.hashpw(password1, BCrypt.gensalt()))
                .build();

        return userRepository.save(user);

    }

    //TODO не кидать общие исключения (сделать детальней)
    private void validateLoginNotTaken(String login) {
        if (userRepository.findUserByLogin(login).isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким логином уже зарегестрирован");
        }
    }

    private User findByLogin(String login) {
        return userRepository.findUserByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Вы ввели неверный логин или пароль"));
    }

    private void validateLogInPasswordEquals(String password1, String password2) {
        if (!BCrypt.checkpw(password1, password2)) {
            throw new IllegalArgumentException("Вы ввели неверный логин или пароль");
        }
    }
}
