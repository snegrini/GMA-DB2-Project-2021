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

    @Column(name = "Answer", nullable = false, length = 45)
    private String answer;

    @ManyToOne
    @JoinColumn(name = "EntryId", insertable = false, updatable = false)
    private EntryEntity entry;

    @ManyToOne
    @JoinColumn(name = "QuestionId", insertable = false, updatable = false)
    private QuestionEntity question;

    public AnswerEntity() {
    }

    public AnswerEntity(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public EntryEntity getEntry() {
        return entry;
    }

    public void setEntry(EntryEntity entry) {
        this.entry = entry;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }
}
