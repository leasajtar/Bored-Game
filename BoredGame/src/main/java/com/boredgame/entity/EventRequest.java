package com.boredgame.entity;

public class EventRequest {
    private String game_name;
    private Integer max_players;
    private Integer cafe_id;
    private String level;

    public String getGame_name()           { return game_name; }
    public void setGame_name(String g)     { this.game_name = g; }
    public Integer getMax_players()        { return max_players; }
    public void setMax_players(Integer m)  { this.max_players = m; }
    public Integer getCafe_id()            { return cafe_id; }
    public void setCafe_id(Integer c)      { this.cafe_id = c; }
    public String getLevel()               { return level; }
    public void setLevel(String l)         { this.level = l; }
}