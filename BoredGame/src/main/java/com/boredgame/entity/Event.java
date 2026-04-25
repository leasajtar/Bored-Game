// Event.java
package com.boredgame.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "event_datetime")
    private LocalDateTime eventDatetime;

    @Column(name = "game_name")
    private String gameName;

    @Column(name = "max_players")
    private int maxPlayers;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;

    public int getId() { return id; }
    public LocalDateTime getEventDatetime() { return eventDatetime; }
    public String getGameName() { return gameName; }
    public int getMaxPlayers() { return maxPlayers; }
    public String getStatus() { return status; }
    public Cafe getCafe() { return cafe; }
    public User getOrganizer() { return organizer; }
}