package com.boredgame.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "joinings")
public class Joining {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }

    public Users getUser() { return user; }
    public void setUser(Users user) { this.user = user; }
}