package ru.plenkkovii.weather.service;

import org.springframework.stereotype.Service;
import ru.plenkkovii.weather.model.Session;
import ru.plenkkovii.weather.model.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemorySessionService implements SessionService {

    private final Map<UUID, Session> sessions = new ConcurrentHashMap<>();

    private static final Duration SESSION_LIFETIME = Duration.ofSeconds(10);

    @Override
    public UUID saveSession(User user) {
        UUID uuid = UUID.randomUUID();
        sessions.put(uuid, new Session(uuid, user, LocalDateTime.now().plus(SESSION_LIFETIME)));
        // тут я два раза храню uuid, как ключ и как поле в Session, не хочу добавлять новый класс,
        // буду пользощваться который будет в БД

        return uuid;
    }

    @Override
    public Optional<Session> getSession(UUID uuid) {
        Session session = sessions.get(uuid);
        if (session != null && !isExpired(session)) {
            return Optional.of(sessions.get(uuid));
        }
        return Optional.empty();
    }

    @Override
    public void deleteSession(UUID uuid) {
        sessions.remove(uuid);
    }

    private boolean isExpired(Session session) {
        //TODO убрать вариант для отладки, оставить одной строчкой
//        return session.getExpiresAt().isBefore(LocalDateTime.now());
        boolean expired = session.getExpiresAt().isBefore(LocalDateTime.now());
        if (expired) {
            System.out.println("session is expired");
        }
        return expired;
    }

}
