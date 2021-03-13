package it.polimi.db2.gma.GMAEJB.entities;

import javax.persistence.*;

@Entity
@Table(name = "review")
@IdClass(ReviewEntityPK.class)
public class ReviewEntity {
    private int id;
    private int productId;
    private int userId;
    private String review;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @Column(name = "ProductId", nullable = false)
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Id
    @Column(name = "UserId", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "Review", nullable = false, length = 45)
    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
