package it.polimi.db2.gma.GMAEJB.utils;

import java.sql.Date;

public class QuestionnaireInfo {
    int id;
    Date date;
    String productName;

    public QuestionnaireInfo(int id, Date date, String productName) {
        this.id = id;
        this.date = date;
        this.productName = productName;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getProductName() {
        return productName;
    }
}
