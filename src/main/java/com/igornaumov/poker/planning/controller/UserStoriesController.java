package com.igornaumov.poker.planning.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.igornaumov.controller.UserStoriesApi;
import com.igornaumov.model.UserStoryRequest;
import com.igornaumov.model.UserStoryResponse;
import com.igornaumov.model.UserStoryStatus;
import com.igornaumov.model.UserStoryStatusUpdateRequest;
import com.igornaumov.poker.planning.entity.SessionEntity;
import com.igornaumov.poker.planning.entity.UserStoryEntity;
import com.igornaumov.poker.planning.repository.SessionRepository;
import com.igornaumov.poker.planning.repository.UserStoryRepository;

@RestController
public class UserStoriesController implements UserStoriesApi {

    private final SessionRepository sessionRepository;
    private final UserStoryRepository userStoryRepository;

    public UserStoriesController(SessionRepository sessionRepository, UserStoryRepository userStoryRepository) {
        this.sessionRepository = sessionRepository;
        this.userStoryRepository = userStoryRepository;
    }

    @Override
    public ResponseEntity<UserStoryResponse> addUserStory(String sessionId, UserStoryRequest userStoryRequest) {
        Optional<SessionEntity> sessionOptional = sessionRepository.findById(sessionId);
        if (sessionOptional.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
        }

        UserStoryEntity saved = userStoryRepository.save(
            new UserStoryEntity(
                userStoryRequest.getDescription(),
                sessionOptional.get())
        );
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(toResponse(saved));
    }

    @Override
    public ResponseEntity<List<UserStoryResponse>> getStoriesInSession(String sessionId) {
        Optional<SessionEntity> sessionOptional = sessionRepository.findById(sessionId);
        return sessionOptional
            .map(entity -> ResponseEntity.ok(userStoryRepository.findBySessionId(sessionId)
                .stream()
                .map(UserStoriesController::toResponse)
                .toList()))
            .orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build());
    }

    @Override
    public ResponseEntity<UserStoryStatus> getUserStoryStatus(String sessionId, String userStoryId) {
        Optional<SessionEntity> sessionOptional = sessionRepository.findById(sessionId);
        if (sessionOptional.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
        }
        return userStoryRepository.findById(userStoryId)
            .map(status -> ResponseEntity.ok(toStatusResponse(status)))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<UserStoryResponse> updateUserStoryStatus(String sessionId, String userStoryId,
                                                                   UserStoryStatusUpdateRequest userStoryStatusUpdateRequest) {
        Optional<SessionEntity> sessionOptional = sessionRepository.findById(sessionId);
        if (sessionOptional.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
        }
        Optional<UserStoryEntity> userStoryOptional = userStoryRepository.findById(userStoryId);
        if (userStoryOptional.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
        }

        return userStoryOptional
            .map(entity -> {
                entity.setUserStoryStatus(userStoryStatusUpdateRequest.getStatus());
                return ResponseEntity.ok(toResponse(entity));
            })
            .orElse(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @Override
    public ResponseEntity<Void> deleteUserStory(String sessionId, String userStoryId) {
        Optional<SessionEntity> sessionOptional = sessionRepository.findById(sessionId);
        sessionOptional
            .ifPresent(session -> userStoryRepository.deleteById(userStoryId));
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build();
    }

    private static UserStoryResponse toResponse(UserStoryEntity entity) {
        return new UserStoryResponse()
            .id(entity.getId())
            .description(entity.getDescription());
    }

    private static UserStoryStatus toStatusResponse(UserStoryEntity entity) {
        return new UserStoryStatus(entity.getUserStoryStatus(), 0);
    }

}
