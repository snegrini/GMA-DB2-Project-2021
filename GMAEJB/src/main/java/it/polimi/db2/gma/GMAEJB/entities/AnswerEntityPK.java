package it.polimi.db2.gma.GMAEJB.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class AnswerEntityPK implements Serializable {
    private int entryId;
    private int questionId;

    @Column(name = "EntryId", nullable = false)
    @Id
    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    @Column(name = "QuestionId", nullable = false)
    @Id
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
