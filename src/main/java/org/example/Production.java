package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Production implements Comparable {
    private String title;
    private List<String> directors;
    private List<String> actors;
    private List<Genre> genres;
    private List<Rating> ratings;
    private String plot;
    private Double averageRating;

    @JsonIgnore
    private ImageIcon imageIconHome;
    @JsonIgnore
    private ImageIcon imageIconDescription;

    @JsonIgnore
    private List<User> contributors;
    @JsonIgnore
    private List<User> ratingUsers;
    @JsonIgnore
    private List<User> favoriteUsers;

    public Production() {
        directors = new ArrayList<>();
        actors = new ArrayList<>();
        genres = new ArrayList<>();
        ratings = new ArrayList<>();
        ratingUsers = new ArrayList<>();
        favoriteUsers = new ArrayList<>();
        contributors = new ArrayList<>();
    }

    public void notifyAll(Rating rating) {
       notifyFavoriteUsers(rating);
       notifyRatingUsers(rating);
       notifyContributor(rating);
    }

    public Double calculateAverageRating() {
        Double average = 0.0;
        if (getRatings().isEmpty()) {
            return 1.0;
        }
        for (Rating rating : getRatings()) {
            average = average + (double) rating.getRating();
        }

        return average / getRatings().size();
    }

    public void notifyRatingUsers(Rating rating) {
        String notification = "Filmul " + title + " pe care l-ai evaluat a primit un review de la utilizatorul \"" +
                rating.getUsername() + "\" -> " + rating.getRating();
        for (User user : ratingUsers) {
            if (!user.getUsername().equals(rating.getUsername()))
                user.update(notification);
        }
    }

    public void notifyFavoriteUsers(Rating rating) {
        String notification = "Filmul " + title + " pe care il ai in lista de favorite a primit un review de la" +
                " utilizatorul \"" + rating.getUsername() + "\" -> " + rating.getRating();
        for (User user : favoriteUsers) {
            if (!user.getUsername().equals(rating.getUsername()))
                user.update(notification);
        }
    }

    public void notifyContributor(Rating rating) {
        String notification = "Filmul " + title + " pe care l-ai adaugat a primit un review de la utilizatorul \"" +
                    rating.getUsername() + "\" -> " + rating.getRating();

        for (User user : contributors) {
            if (!user.getUsername().equals(rating.getUsername()))
                user.update(notification);
        }
    }

    public List<User> getFavoriteUsers() {
        return favoriteUsers;
    }

    public void setFavoriteUsers(List<User> favoriteUsers) {
        this.favoriteUsers = favoriteUsers;
    }

    public List<User> getRatingUsers() {
        return ratingUsers;
    }

    public List<User> getContributors() {
        return contributors;
    }

    public void setContributors(List<User> contributors) {
        this.contributors = contributors;
    }

    public void setRatingUsers(List<User> ratingUsers) {
        this.ratingUsers = ratingUsers;
    }

    public String getTitle() {
        return title;
    }

    public ImageIcon getImageIconDescription() {
        return imageIconDescription;
    }

    public ImageIcon getImageIconHome() {
        return imageIconHome;
    }

    public void setImageIconDescription(ImageIcon imageIconDescription) {
        this.imageIconDescription = imageIconDescription;
    }

    public void setImageIconHome(ImageIcon imageIconHome) {
        this.imageIconHome = imageIconHome;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public List<String> getActors() {
        return actors;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public String getPlot() {
        return plot;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public Rating getRating(String username) {
        for (Rating rating : ratings) {
            if (rating.getUsername().equals(username)) {
                return rating;
            }
        }
        return null;
    }


    //public abstract void displayInfo() {}

    public boolean findRating(User user) {
        for (Rating rating : ratings) {
            if (rating.getUsername().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public int compareTo(@NotNull Object o) {
        if (o instanceof Production) {
            Production production = (Production) o;
            return title.compareTo(production.getTitle());
        }
        Actor actor = (Actor) o;
        return title.compareTo(actor.getName());
    }
}
