package ru.plenkkovii.weather.archive;

import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.plenkkovii.weather.model.User;
import ru.plenkkovii.weather.repository.UserRepository;
import ru.plenkkovii.weather.service.UserService;

import java.time.Duration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@Transactional
public class LoginControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void shouldLoginUser() throws Exception {
        String login = "test";
        String password = "test";

        userRepository.save(User.builder()
                .login(login)
                .password(BCrypt.hashpw(password, BCrypt.gensalt()))
                .build());

        mvc.perform(post("/login")
                        .param("login", login)
                        .param("password", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"))
                .andExpect(cookie().exists("SESSION_UUID")).andReturn();
    }

    @Test
    public void shouldNotLoginUserIfPasswordIsWrong() throws Exception {
        String login = "test";
        String rightPassword = "true";
        String wrongPassword = "false";

        userRepository.save(User.builder()
                .login(login)
                .password(BCrypt.hashpw(rightPassword, BCrypt.gensalt()))
                .build());

        mvc.perform(post("/login")
                        .param("login", login)
                        .param("password", wrongPassword))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void shouldLogoutIfSessionIsExpired(@Value("${session.duration}") Duration sessionDuration) throws Exception {
        String login = "test";
        String password = "test";

        userService.registerAndLogin(login, password);

        mvc.perform(post("/login")
                        .param("login", login)
                        .param("password", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

        Thread.sleep(sessionDuration);

        mvc.perform(get("/home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));
    }
}
