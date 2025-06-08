package ru.plenkkovii.weather.service;

import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.plenkkovii.weather.dto.LocationApiResponseDTO;
import ru.plenkkovii.weather.exception.LocationAlreadyExistException;
import ru.plenkkovii.weather.exception.LoginAlreadyExistException;
import ru.plenkkovii.weather.exception.SessionExpiredException;
import ru.plenkkovii.weather.exception.UserNotFoundException;
import ru.plenkkovii.weather.model.Location;
import ru.plenkkovii.weather.model.Session;
import ru.plenkkovii.weather.model.User;
import ru.plenkkovii.weather.repository.LocationRepository;
import ru.plenkkovii.weather.repository.SessionRepository;
import ru.plenkkovii.weather.repository.UserRepository;

import java.util.UUID;

@Service
@AllArgsConstructor
public class LocationService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    public Location addLocationRequest(LocationApiResponseDTO locationApiResponseDTO, UUID sessionUuid) {

        Session session = sessionRepository.findById(sessionUuid).orElseThrow(() -> new SessionExpiredException("Сессия не найдена"));

        User user = userRepository.findById(session.getUser().getId()).orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        Location location = Location.builder()
                .name(locationApiResponseDTO.getName())
                .longitude(locationApiResponseDTO.getLongitude())
                .latitude(locationApiResponseDTO.getLatitude())
                .user(user)
                .build();

        try {
            locationRepository.save(location);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException && e.getMessage().contains("unique_locations_coordinates")) {
                throw new LocationAlreadyExistException("Эта локация уже сохранена для вашего аккаунта");
            }

            throw e;
        }

        return location;
    }
    @Transactional
    public void deleteLocationByName(String name) {
        locationRepository.deleteByName(name);
    }
}
