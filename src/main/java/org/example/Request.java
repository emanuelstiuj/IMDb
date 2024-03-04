package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Request {
    private RequestTypes type;
    @JsonIgnore
    private LocalDateTime createdDate;
    private String actorName;
    private String description;
    private String username;
    private String movieTitle;
    private String to;
    private int id;

    public Request() {}

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTo() {
        return to;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public RequestTypes getType() {
        return type;
    }

    public String getActorName() {
        return actorName;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setType(RequestTypes type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}