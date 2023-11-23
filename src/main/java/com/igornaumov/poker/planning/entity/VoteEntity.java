package com.igornaumov.poker.planning.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "votes")
@IdClass(VoteId.class)
public class VoteEntity {

    @Id
    @Column(name = "user_id")
    private String userId;
    @Id
    @Column(name = "story_id")
    private String userStoryId;
    @Column(name = "vote_value")
    private String value;
    @Column(name = "session_id")
    private String sessionId;

    public VoteEntity() {
    }

    public VoteEntity(String userId, String userStoryId, String value, String sessionId) {
        this.userId = userId;
        this.userStoryId = userStoryId;
        this.value = value;
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserStoryId() {
        return userStoryId;
    }

    public String getValue() {
        return value;
    }

    public String getSessionId() {
        return sessionId;
    }

}
