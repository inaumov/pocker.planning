package com.igornaumov.poker.planning.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.igornaumov.controller.VotesApi;
import com.igornaumov.model.VoteRequest;
import com.igornaumov.poker.planning.entity.SessionEntity;
import com.igornaumov.poker.planning.entity.VoteEntity;
import com.igornaumov.poker.planning.repository.SessionRepository;
import com.igornaumov.poker.planning.repository.VotesRepository;

@RestController
public class VotesController implements VotesApi {

    private final SessionRepository sessionRepository;
    private final VotesRepository votesRepository;

    public VotesController(SessionRepository sessionRepository, VotesRepository votesRepository) {
        this.sessionRepository = sessionRepository;
        this.votesRepository = votesRepository;
    }

    @Override
    public ResponseEntity<Void> vote(String sessionId, String userId, VoteRequest voteRequest) {
        Optional<SessionEntity> sessionOptional = sessionRepository.findById(sessionId);
        if (sessionOptional.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
        }
        votesRepository.save(new VoteEntity(userId, voteRequest.getUserStoryId(), voteRequest.getValue(), sessionId));
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }

}
