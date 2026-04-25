package com.boredgame.entity;

public class EventDTO {
    private Integer id;
    private String game_name;           // "Catan"
    private String cafe_name;          // "Kafić Botun"
    private String event_datetime;        // formatirano: "Subota, 26.4. u 18:00"
    private Integer max_players;          // 4
    private Integer currentPlayers;     // koliko se već priključilo + organizator
    private Integer freeSpaces;     // maxIgraca - trenutnoIgraca
    private boolean userAccepted; // je li ovaj user već kliknuo "Pridruži se"
    private boolean isOrganizer;// je li ovaj uje Organizatorser organizator

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public String getGame_name() {return game_name;}
    public void setGame_name(String game_name) {this.game_name = game_name;}
    public String getCafe_name() {return cafe_name;}
    public void setCafe_name(String cafe_name) {this.cafe_name = cafe_name;}
    public String getEvent_datetime() {return event_datetime;}
    public void setEvent_datetime(String event_datetime) {this.event_datetime = event_datetime;}
    public Integer getMax_players() {return max_players;}
    public void setMax_players(Integer max_players) {this.max_players = max_players;}
    public Integer getCurrentPlayers() {return currentPlayers;}
    public void setCurrentPlayers(Integer currentPlayers) {this.currentPlayers = currentPlayers;}
    public Integer getFreeSpaces() {return freeSpaces;}
    public void setFreeSpaces(Integer freeSpaces) {this.freeSpaces = freeSpaces;}
    public boolean isUserAccepted() {return userAccepted;}
    public void setUserAccepted(boolean userAccepted) {this.userAccepted = userAccepted;}
    public boolean isOrganizer() {return isOrganizer;}
    public void setOrganizer(boolean organizer) {isOrganizer = organizer;}
}
