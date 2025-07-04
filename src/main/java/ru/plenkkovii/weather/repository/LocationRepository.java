package ru.plenkkovii.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.plenkkovii.weather.model.Location;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    List<Location> findByUserId(int userId);

    void deleteByNameAndLatitudeAndLongitude(String name, double latitude, double longitude);
}