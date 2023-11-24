package com.igornaumov.poker.planning.entity;


import com.igornaumov.model.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_stories")
public class UserStoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "story_id")
    private String id;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    @Convert(converter = StatusConverter.class)
    private Status userStoryStatus;
    @Column(name = "session_id")
    private String sessionId;

    public UserStoryEntity() {
    }

    public UserStoryEntity(String description, String sessionId) {
        this.description = description;
        this.sessionId = sessionId;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Status getUserStoryStatus() {
        return userStoryStatus;
    }

    public void setUserStoryStatus(Status userStoryStatus) {
        this.userStoryStatus = userStoryStatus;
    }

    public String getSessionId() {
        return sessionId;
    }

}
