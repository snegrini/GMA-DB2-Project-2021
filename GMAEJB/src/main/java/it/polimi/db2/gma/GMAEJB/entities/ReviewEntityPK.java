package it.polimi.db2.gma.GMAEJB.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class ReviewEntityPK implements Serializable {
    private int productId;
    private int userId;

    @Column(name = "ProductId", nullable = false)
    @Id
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Column(name = "UserId", nullable = false)
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReviewEntityPK that = (ReviewEntityPK) o;

        if (productId != that.productId) return false;
        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = productId;
        result = 31 * result + userId;
        return result;
    }
}
