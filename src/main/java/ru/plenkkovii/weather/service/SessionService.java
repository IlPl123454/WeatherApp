package ru.plenkkovii.weather.service;

import jakarta.servlet.http.Cookie;
import ru.plenkkovii.weather.model.Session;
import ru.plenkkovii.weather.model.User;

import java.util.Optional;
import java.util.UUID;

public interface SessionService {

    UUID createSession(User user);

    Optional<Session> getSession(UUID sessionId);

    Optional<Session> getSessionFromCookie(Cookie[] cookies);


    void deleteSession(UUID sessionId);


}

