package ru.plenkkovii.weather.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.plenkkovii.weather.model.Session;
import ru.plenkkovii.weather.model.User;
import ru.plenkkovii.weather.repository.SessionRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


@Service
@Primary
public class DataBaseSessionService implements SessionService {

    SessionRepository sessionRepository;

    @Value("${session.duration_h}")
    private int durationH;

    @Value("${session.duration_m}")
    private int durationM;

    @Value("${session.duration_s}")
    private int durationS;

    public DataBaseSessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public UUID createSession(User user) {
        Duration sessionLifetime = Duration.ofHours(durationH)
                .plus(Duration.ofMinutes(durationM)
                        .plus(Duration.ofSeconds(durationS)));

        Session session = Session.builder()
                .id(UUID.randomUUID())
                .user(user)
                .expiresAt(Instant.now().plus(sessionLifetime))
                .build();

        sessionRepository.save(session);

        return session.getId();
    }

    @Override
    public Optional<Session> getSession(UUID sessionId) {
        Optional<Session> session = sessionRepository.findById(sessionId);

        if (session.isPresent() && session.get().getExpiresAt().isAfter(Instant.now())) {
            return session;
        }

        sessionRepository.deleteById(sessionId);
        return Optional.empty();
    }

    @Override
    public void deleteSession(UUID sessionId) {
        sessionRepository.deleteById(sessionId);
    }
}
