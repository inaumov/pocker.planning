package com.igornaumov.poker.planning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.igornaumov.poker.planning.entity.UserStoryEntity;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStoryEntity, String> {

    @Query("SELECT userStory FROM UserStoryEntity userStory WHERE userStory.session = :sessionId")
    List<UserStoryEntity> findBySessionId(String sessionId);
}
