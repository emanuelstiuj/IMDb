package org.example;

public class Movie extends Production{
    private String duration;
    private int releaseYear;

    public Movie() {}

    public String getDuration() {
        return duration;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
}
