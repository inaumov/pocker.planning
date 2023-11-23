package com.igornaumov.poker.planning.controller;

import static com.igornaumov.model.UserStoryStatus.StatusEnum.VOTED;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.igornaumov.controller.VotesApi;
import com.igornaumov.poker.planning.entity.SessionEntity;
import com.igornaumov.poker.planning.entity.UserStoryEntity;
import com.igornaumov.poker.planning.repository.SessionRepository;
import com.igornaumov.poker.planning.repository.UserStoryRepository;

@RestController
public class VotesController implements VotesApi {

    private final SessionRepository sessionRepository;
    private final UserStoryRepository userStoryRepository;

    public VotesController(SessionRepository sessionRepository, UserStoryRepository userStoryRepository) {
        this.sessionRepository = sessionRepository;
        this.userStoryRepository = userStoryRepository;
    }

    @Override
    public ResponseEntity<Void> stopVoting(String sessionId, String userStoryId) {
        Optional<SessionEntity> sessionOptional = sessionRepository.findById(sessionId);
        return sessionOptional
            .map(session -> {
                Optional<UserStoryEntity> userStoryOptional = userStoryRepository.findById(userStoryId);
                userStoryOptional.ifPresent(entity -> entity.setUserStoryStatus(VOTED));
                return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
            })
            .orElse(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

}
