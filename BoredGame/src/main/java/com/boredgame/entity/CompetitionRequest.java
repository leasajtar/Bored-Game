// CompetitionRequest.java
package com.boredgame.entity;

public class CompetitionRequest {
    private String game_type;
    private String time;
    private String date;
    private Double entry_fee;
    private Integer max_players;
    private Integer cafe_id;

    public String getGame_type() { return game_type; }
    public void setGame_type(String game_type) { this.game_type = game_type; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public Double getEntry_fee() { return entry_fee; }
    public void setEntry_fee(Double entry_fee) { this.entry_fee = entry_fee; }

    public Integer getMax_players() { return max_players; }
    public void setMax_players(Integer max_players) { this.max_players = max_players; }

    public Integer getCafe_id() { return cafe_id; }
    public void setCafe_id(Integer cafe_id) { this.cafe_id = cafe_id; }
}