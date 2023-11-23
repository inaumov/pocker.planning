package com.igornaumov.poker.planning.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.igornaumov.controller.UsersApi;
import com.igornaumov.model.UserRequest;
import com.igornaumov.model.UserResponse;
import com.igornaumov.poker.planning.entity.UserEntity;
import com.igornaumov.poker.planning.entity.SessionEntity;
import com.igornaumov.poker.planning.repository.UserRepository;
import com.igornaumov.poker.planning.repository.SessionRepository;

@RestController
public class UsersController implements UsersApi {

    private final SessionRepository sessionRepository;
    private final UserRepository usersRepository;

    public UsersController(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.usersRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<UserResponse>> getUsersInSession(String sessionId) {
        Optional<SessionEntity> sessionOptional = sessionRepository.findById(sessionId);
        return sessionOptional
            .map(entity -> ResponseEntity.ok(usersRepository.findBySessionId(sessionId)
                .stream()
                .map(UsersController::toResponse)
                .toList()))
            .orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build());
    }

    @Override
    public ResponseEntity<UserResponse> joinSession(String sessionId, UserRequest userRequest) {
        Optional<SessionEntity> sessionOptional = sessionRepository.findById(sessionId);
        if (sessionOptional.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
        }

        UserEntity saved = usersRepository.save(
            new UserEntity(
                userRequest.getName(),
                sessionOptional.get())
        );
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(toResponse(saved));
    }

    private static UserResponse toResponse(UserEntity user) {
        return new UserResponse(user.getId(), user.getName());
    }

}
