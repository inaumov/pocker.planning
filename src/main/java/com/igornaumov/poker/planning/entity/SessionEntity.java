package com.igornaumov.poker.planning.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sessions")
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "session_id")
    private String id;
    @Column(name = "session_name")
    private String name;
    @Column(name = "deck_type")
    private String deckType;

    public SessionEntity() {

    }

    public SessionEntity(String name, String deckType) {
        this.name = name;
        this.deckType = deckType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDeckType() {
        return deckType;
    }

}
