package it.polimi.db2.gma.GMAEJB.utils;

public class Review {
    String username;
    String review;

    public Review(String username, String review) {
        this.username = username;
        this.review = review;
    }

    public String getUsername() {
        return username;
    }

    public String getReview() {
        return review;
    }
}
