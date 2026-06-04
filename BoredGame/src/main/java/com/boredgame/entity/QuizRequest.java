package com.boredgame.entity;

public class QuizRequest {
    private String description;
    private String date;
    private String time;
    private Double entry_fee;
    private Integer cafe_id;

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public Double getEntry_fee() { return entry_fee; }
    public void setEntry_fee(Double entry_fee) { this.entry_fee = entry_fee; }

    public Integer getCafe_id() { return cafe_id; }
    public void setCafe_id(Integer cafe_id) { this.cafe_id = cafe_id; }
}