package com.boredgame.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    private Double entry_fee;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe_id;

    public Integer getId() { return id; }
    public String getDescription() { return description; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public Double getEntryFee() { return entry_fee; }
    public Cafe getCafe() { return cafe_id; }

    public void setDescription(String description) { this.description = description; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setTime(LocalTime time) { this.time = time; }
    public void setEntryFee(Double entryFee) { this.entry_fee = entryFee; }
    public void setCafe(Cafe cafe) { this.cafe_id = cafe; }
}