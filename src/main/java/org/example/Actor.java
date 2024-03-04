package org.example;

import java.util.ArrayList;
import java.util.List;

public class Actor implements Comparable {
    private String name;
    private List<Performance> performances;
    private String biography;

    public Actor() {
        performances = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getBiography() {
        return biography;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    public boolean hasMovies() {
        for (Performance performance : performances) {
            if (performance.getType().equals("Movie")) {
                return true;
            }
        }
        return false;
    }

    public boolean hasSeries() {
        for (Performance performance : performances) {
            if (performance.getType().equals("Series")) {
                return true;
            }
        }
        return false;
    }


    @Override
    public int compareTo(Object o) {
        if (o instanceof Actor) {
            Actor actor = (Actor) o;
            return name.compareTo(actor.getName());
        }
        Production production = (Production) o;
        return name.compareTo(production.getTitle());
    }
}
