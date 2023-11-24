package com.igornaumov.poker.planning.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.igornaumov.controller.VotesApi;
import com.igornaumov.model.Status;
import com.igornaumov.model.VoteRequest;
import com.igornaumov.poker.planning.entity.SessionEntity;
import com.igornaumov.poker.planning.entity.UserStoryEntity;
import com.igornaumov.poker.planning.entity.VoteEntity;
import com.igornaumov.poker.planning.repository.SessionRepository;
import com.igornaumov.poker.planning.repository.UserStoryRepository;
import com.igornaumov.poker.planning.repository.VotesRepository;

@RestController
public class VotesController implements VotesApi {

    private final SessionRepository sessionRepository;
    private final VotesRepository votesRepository;
    private final UserStoryRepository userStoryRepository;

    public VotesController(SessionRepository sessionRepository, VotesRepository votesRepository, UserStoryRepository userStoryRepository) {
        this.sessionRepository = sessionRepository;
        this.votesRepository = votesRepository;
        this.userStoryRepository = userStoryRepository;
    }

    @Override
    public ResponseEntity<Void> vote(String sessionId, String userId, VoteRequest voteRequest) {
        Optional<SessionEntity> sessionOptional = sessionRepository.findById(sessionId);
        if (sessionOptional.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
        }
        Optional<UserStoryEntity> userStoryOptional = userStoryRepository.findById(voteRequest.getUserStoryId());
        if (userStoryOptional.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
        }
        UserStoryEntity userStoryEntity = userStoryOptional.get();
        if (userStoryEntity.getUserStoryStatus() != Status.VOTING) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        votesRepository.save(new VoteEntity(userId, voteRequest.getUserStoryId(), voteRequest.getValue(),
            sessionOptional.get().getId()));
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }

}
