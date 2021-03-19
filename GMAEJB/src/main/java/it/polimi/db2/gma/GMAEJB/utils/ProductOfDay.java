package it.polimi.db2.gma.GMAEJB.utils;

import java.util.List;

public class ProductOfDay {
    String name;
    String image;
    List<Review> reviews;

    public ProductOfDay(String name, String image, List<Review> reviews) {
        this.name = name;
        this.image = image;
        this.reviews = reviews;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public List<Review> getReviews() {
        return reviews;
    }
}
