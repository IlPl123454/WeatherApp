package ru.plenkkovii.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.plenkkovii.weather.model.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
}
