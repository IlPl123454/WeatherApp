package ru.plenkkovii.weather.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.plenkkovii.weather.dto.LocationApiResponseDTO;
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

        Session session = sessionRepository.findById(sessionUuid).orElseThrow(() -> new SessionExpiredException("Session not found"));

        User user = userRepository.findById(session.getUser().getId()).orElseThrow(() -> new UserNotFoundException("User not found"));

        Location location = Location.builder()
                .name(locationApiResponseDTO.getName())
                .longitude(locationApiResponseDTO.getLongitude())
                .latitude(locationApiResponseDTO.getLatitude())
                .user(user)
                .build();

        locationRepository.save(location);

        return location;
    }
    @Transactional
    public void deleteLocationByName(String name) {
        locationRepository.deleteByName(name);
    }
}
