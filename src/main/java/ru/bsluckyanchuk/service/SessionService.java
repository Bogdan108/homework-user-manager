package ru.bsluckyanchuk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsluckyanchuk.entity.Sessions;
import ru.bsluckyanchuk.entity.Users;
import ru.bsluckyanchuk.repository.SessionRepository;

import javax.websocket.Session;
import java.time.LocalDateTime;

@Service
public class SessionService {
    @Autowired
    private SessionRepository sessionRepository;

    public Sessions createSession(Users user, String sessionToken, LocalDateTime expiresAt) {
        Sessions session = new Sessions();
        session.setUser(user);
        session.setSessionToken(sessionToken);
        session.setExpiresAt(expiresAt);

        return sessionRepository.save(session);
    }

    public Sessions getSessionByToken(String sessionToken) {
        return sessionRepository.findBySessionToken(sessionToken);
    }
}

