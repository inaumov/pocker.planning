package com.igornaumov.poker.planning.entity;


import com.igornaumov.model.Status;
import com.igornaumov.model.UserStoryStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private Status userStoryStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "session_id")
    private SessionEntity session;

    public UserStoryEntity() {
    }

    public UserStoryEntity(String description, SessionEntity session) {
        this.description = description;
        this.session = session;
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

    public SessionEntity getSession() {
        return session;
    }

}
