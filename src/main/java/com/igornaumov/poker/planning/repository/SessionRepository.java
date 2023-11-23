package com.igornaumov.poker.planning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.igornaumov.poker.planning.entity.SessionEntity;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, String> {

}
