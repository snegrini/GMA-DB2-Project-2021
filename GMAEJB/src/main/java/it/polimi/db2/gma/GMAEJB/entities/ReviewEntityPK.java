package it.polimi.db2.gma.GMAEJB.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ReviewEntityPK implements Serializable {
    private int productId;
    private int userId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
