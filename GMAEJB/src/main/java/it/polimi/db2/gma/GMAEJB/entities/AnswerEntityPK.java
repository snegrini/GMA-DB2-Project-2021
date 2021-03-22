package it.polimi.db2.gma.GMAEJB.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AnswerEntityPK implements Serializable {

    private int entryId;

    private int questionId;


    public AnswerEntityPK() {

    }

    public AnswerEntityPK(int entryId, int questionId) {
        this.entryId = entryId;
        this.questionId = questionId;
    }

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
