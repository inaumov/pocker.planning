package com.igornaumov.poker.planning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.igornaumov.poker.planning.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    List<UserEntity> findBySessionId(String sessionId);

    void deleteBySessionId(String sessionId);
}
