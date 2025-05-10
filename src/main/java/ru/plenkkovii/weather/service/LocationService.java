package ru.plenkkovii.weather.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.plenkkovii.weather.dto.LocationApiResponseDTO;
import ru.plenkkovii.weather.model.Location;
import ru.plenkkovii.weather.model.Session;
import ru.plenkkovii.weather.model.User;
import ru.plenkkovii.weather.repository.LocationRepository;
import ru.plenkkovii.weather.repository.SessionRepository;
import ru.plenkkovii.weather.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LocationService {

    SessionRepository sessionRepository;
    UserRepository userRepository;
    LocationRepository locationRepository;

    public Location addLocation(LocationApiResponseDTO locationApiResponseDTO, UUID userUuid) {

        Optional<Session> session = sessionRepository.findById(userUuid);

        //TODO добавить проверку на то что сессия существует, хотя фильтр проверяет перед этим..
        Optional<User> byId = userRepository.findById(session.get().getUser().getId());

        Location location = Location.builder()
                .name(locationApiResponseDTO.getName())
                .longitude(locationApiResponseDTO.getLongitude())
                .latitude(locationApiResponseDTO.getLatitude())
                .user(byId.get())
                .build();

        locationRepository.save(location);

        return location;
    }
}
