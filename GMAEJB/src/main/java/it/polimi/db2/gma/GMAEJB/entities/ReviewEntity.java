package it.polimi.db2.gma.GMAEJB.entities;

import javax.persistence.*;

/**
 * Weak entity.
 */
@Entity
@Table(name = "review")
public class ReviewEntity {
    @EmbeddedId
    private ReviewEntityPK reviewEntityPK;

    @Column(name = "Review", nullable = false, length = 45)
    private String review;

    @ManyToOne
    @JoinColumn(name = "ProductId", insertable = false, updatable = false)
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "UserId", insertable = false, updatable = false)
    private UserEntity user;

    public ReviewEntityPK getReviewEntityPK() {
        return reviewEntityPK;
    }

    public void setReviewEntityPK(ReviewEntityPK reviewEntityPK) {
        this.reviewEntityPK = reviewEntityPK;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
