package ru.plenkkovii.weather.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.plenkkovii.weather.model.Session;
import ru.plenkkovii.weather.model.User;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemorySessionService implements SessionService {

    private final Map<UUID, Session> sessions = new ConcurrentHashMap<>();

    @Value("${session.duration_h}")
    private int durationH;

    @Value("${session.duration_m}")
    private int durationM;

    @Value("${session.duration_s}")
    private int durationS;


    @Transactional
    @Override
    public UUID createSession(User user) {
        Duration sessionLifetime = Duration.ofHours(durationH)
                .plus(Duration.ofMinutes(durationM)
                        .plus(Duration.ofSeconds(durationS)));

        UUID uuid = UUID.randomUUID();
        sessions.put(uuid, new Session(uuid, user, Instant.now().plus(sessionLifetime)));
        // тут я два раза храню uuid, как ключ и как поле в Session, не хочу добавлять новый класс
        // буду пользощваться который будет в БД

        return uuid;
    }

    @Override
    public Optional<Session> getSession(UUID uuid) {
        Session session = sessions.get(uuid);

        if (session != null && !isExpired(session)) {
            return Optional.of(session);
        }

        sessions.remove(uuid);
        return Optional.empty();
    }

    @Override
    public void deleteSession(UUID uuid) {
        sessions.remove(uuid);
    }

    private boolean isExpired(Session session) {
        return session.getExpiresAt().isBefore(Instant.now());
    }
}
