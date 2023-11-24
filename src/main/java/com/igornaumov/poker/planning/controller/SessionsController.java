package com.igornaumov.poker.planning.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.igornaumov.controller.SessionsApi;
import com.igornaumov.model.SessionRequest;
import com.igornaumov.model.SessionResponse;
import com.igornaumov.poker.planning.entity.SessionEntity;
import com.igornaumov.poker.planning.repository.SessionRepository;
import com.igornaumov.poker.planning.repository.UserRepository;
import com.igornaumov.poker.planning.repository.UserStoryRepository;
import com.igornaumov.poker.planning.repository.VotesRepository;

@RestController
public class SessionsController implements SessionsApi {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final UserStoryRepository userStoryRepository;
    private final VotesRepository votesRepository;

    public SessionsController(SessionRepository sessionRepository, UserRepository userRepository, UserStoryRepository userStoryRepository, VotesRepository votesRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.userStoryRepository = userStoryRepository;
        this.votesRepository = votesRepository;
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
    @Transactional
    public ResponseEntity<Void> deleteSession(String sessionId) {
        Optional<SessionEntity> sessionOptional = sessionRepository.findById(sessionId);
        return sessionOptional
            .map(session -> {
                votesRepository.deleteBySessionId(sessionId);
                userStoryRepository.deleteBySessionId(sessionId);
                userRepository.deleteBySessionId(sessionId);
                sessionRepository.delete(session);
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            })
            .orElse(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    private static SessionResponse toSession(SessionEntity entity) {
        return new SessionResponse(entity.getId(), entity.getName(), entity.getDeckType());
    }

}