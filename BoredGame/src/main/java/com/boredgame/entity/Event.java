package com.boredgame.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String game_name;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "event_datetime")
    private LocalDateTime eventDateTime;
    private Integer max_players;
    private String status;
    private String level;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe_id;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer_id;

    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
    private List<Joining> joinings = new ArrayList<>();

    // --- Getters (unchanged) ---
    public Integer getId()                    { return id; }
    public String getNazivIgre()              { return game_name; }
    public LocalDateTime getDatumVrijeme()    { return eventDateTime; }
    public Integer getMaxPlayers()            { return max_players; }
    public String getStatus()                 { return status; }
    public Cafe getKafic()                    { return cafe_id; }
    public User getOrganizator()              { return organizer_id; }
    public List<Joining> getPridruzivanja()   { return joinings; }
    public String getLevel()                  { return level; }

    // --- Setters (new) ---
    public void setGameName(String gameName)          { this.game_name = gameName; }
    public void setEventDatetime(LocalDateTime dt)    { this.eventDateTime = dt; }
    public void setMaxPlayers(Integer maxPlayers)     { this.max_players = maxPlayers; }
    public void setStatus(String status)              { this.status = status; }
    public void setLevel(String level)                { this.level = level; }
    public void setCafe(Cafe cafe)                    { this.cafe_id = cafe; }
    public void setOrganizer(User organizer)          { this.organizer_id = organizer; }
}