package ru.plenkkovii.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.plenkkovii.weather.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
}
