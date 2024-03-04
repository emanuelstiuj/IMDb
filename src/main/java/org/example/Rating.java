package org.example;

public class Rating implements Comparable<Rating>{
    private String username;
    private int rating;
    private String comment;

    public Rating() {}

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int compareTo(Rating rating) {
        User thisUser = IMDB.getInstance().getUserByUsername(username);
        User ratingUser = IMDB.getInstance().getUserByUsername(rating.username);

        return Integer.compare (ratingUser.getExperience(), thisUser.getExperience());
    }
}
