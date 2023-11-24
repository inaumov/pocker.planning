package com.igornaumov.poker.planning.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String id;
    @Column(name = "nickname")
    private String name;
    @Column(name = "session_id")
    private String sessionId;

    public UserEntity() {
    }

    public UserEntity(String name, String sessionId) {
        this.name = name;
        this.sessionId = sessionId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSessionId() {
        return sessionId;
    }

}
