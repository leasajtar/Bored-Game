package com.boredgame.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class EventRequest {
    private String game_name;
    private Integer max_players;
    private Integer cafe_id;
    private String level;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private CharSequence event_datetime;

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String g) {
        this.game_name = g;
    }

    public Integer getMax_players() {
        return max_players;
    }

    public void setMax_players(Integer m) {
        this.max_players = m;
    }

    public Integer getCafe_id() {
        return cafe_id;
    }

    public void setCafe_id(Integer c) {
        this.cafe_id = c;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String l) {
        this.level = l;
    }

    public CharSequence getEvent_datetime() {
        return event_datetime;
    }

    public void setEvent_datetime(CharSequence event_datetime) {
        this.event_datetime = event_datetime;
    }
}