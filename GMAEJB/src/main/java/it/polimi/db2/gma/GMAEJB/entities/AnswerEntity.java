package it.polimi.db2.gma.GMAEJB.entities;

import javax.persistence.*;

/**
 * Weak entity.
 */
@Entity
@Table(name = "answer")
public class AnswerEntity {
    @EmbeddedId
    private AnswerEntityPK answerEntityPK;
    private String answer;

    @ManyToOne
    @JoinColumn(name = "EntryId", insertable = false, updatable = false)
    private EntryEntity entry;

    @ManyToOne
    @JoinColumn(name = "QuestionId", insertable = false, updatable = false)
    private QuestionEntity question;

    @Column(name = "Answer", nullable = false, length = 45)
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
