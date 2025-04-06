package ru.plenkkovii.weather.service;

import ru.plenkkovii.weather.model.Session;
import ru.plenkkovii.weather.model.User;

import java.util.Optional;
import java.util.UUID;

public interface SessionService {

    UUID saveSession(User user);

    Optional<Session> getSession(UUID sessionId);

    void deleteSession(UUID sessionId);
}

