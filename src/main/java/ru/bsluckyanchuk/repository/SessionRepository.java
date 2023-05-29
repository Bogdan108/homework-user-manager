package ru.bsluckyanchuk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bsluckyanchuk.entity.Sessions;

@Repository
public interface SessionRepository extends JpaRepository<Sessions, Long> {
    Sessions findBySessionToken(String sessionToken);
}