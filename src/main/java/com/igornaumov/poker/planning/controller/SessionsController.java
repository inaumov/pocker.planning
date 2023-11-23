package com.igornaumov.poker.planning.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.igornaumov.controller.SessionsApi;
import com.igornaumov.model.SessionRequest;
import com.igornaumov.model.SessionResponse;
import com.igornaumov.poker.planning.entity.SessionEntity;
import com.igornaumov.poker.planning.repository.SessionRepository;

@RestController
public class SessionsController implements SessionsApi {

    private final SessionRepository sessionRepository;

    public SessionsController(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public ResponseEntity<SessionResponse> getSessionDetails(String sessionId) {
        Optional<SessionEntity> sessionOptional = sessionRepository.findById(sessionId);
        return sessionOptional
            .map(session -> ResponseEntity.ok(toSession(session)))
            .orElse(new ResponseEntity<SessionResponse>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<SessionResponse> createNewSession(SessionRequest request) {
        SessionEntity newSessionEntity = sessionRepository.save(
            new SessionEntity(request.getTitle(), request.getDeckType()));
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(toSession(newSessionEntity));
    }

    @Override
    public ResponseEntity<Void> deleteSession(String sessionId) {
        Optional<SessionEntity> sessionOptional = sessionRepository.findById(sessionId);
        return sessionOptional
            .map(session -> {
                sessionRepository.delete(session);
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            })
            .orElse(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    private static SessionResponse toSession(SessionEntity entity) {
        return new SessionResponse(entity.getId(), entity.getName(), entity.getDeckType());
    }

}