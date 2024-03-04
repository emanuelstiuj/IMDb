package org.example;

import java.util.*;

public class Series extends Production{
    private int releaseYear;
    private int numSeasons;
    private Map<String, List<Episode>> seasons;

    public Series() {
        seasons = new TreeMap<>();
    }

    public void addSeason(String seasonName, List<Episode> episodes) {
        seasons.put(seasonName, episodes);
    }

    public void setSeasons(Map<String, List<Episode>> seasons) {
        this.seasons = seasons;
    }

    public void setNumSeasons(int numSeasons) {
        this.numSeasons = numSeasons;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public int getNumSeasons() {
        return numSeasons;
    }

    public Map<String, List<Episode>> getSeasons() {
        return seasons;
    }
}
