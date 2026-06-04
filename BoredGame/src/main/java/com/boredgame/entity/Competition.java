package com.boredgame.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "competitions")
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String game_type;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private Double entry_fee;
    private Integer max_players;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe_id;

    @OneToMany(mappedBy = "competition", fetch = FetchType.EAGER)
    private List<CompetitionJoining> joinings = new ArrayList<>();

    public List<CompetitionJoining> getJoinings() {
        return joinings;
    }

    public Integer getId() { return id; }
    public String getGameType() { return game_type; }
    public LocalTime getTime() { return time; }
    public LocalDate getDate() { return date; }
    public Double getEntryFee() { return entry_fee; }
    public Integer getMaxPlayers() { return max_players; }
    public Cafe getCafe() { return cafe_id; }

    public void setGameType(String gameType) { this.game_type = gameType; }
    public void setTime(LocalTime time) { this.time = time; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setEntryFee(Double entryFee) { this.entry_fee = entryFee; }
    public void setMaxPlayers(Integer maxPlayers) { this.max_players = maxPlayers; }
    public void setCafe(Cafe cafe) { this.cafe_id = cafe; }
}