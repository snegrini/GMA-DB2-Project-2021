package it.polimi.db2.gma.GMAEJB;

import javax.persistence.*;

@Entity
@Table(name = "answer")
@IdClass(AnswerEntityPK.class)
public class AnswerEntity {
    private int entryId;
    private int questionId;
    private String answer;

    @Id
    @Column(name = "EntryId", nullable = false)
    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    @Id
    @Column(name = "QuestionId", nullable = false)
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Basic
    @Column(name = "Answer", nullable = false, length = 45)
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
