package it.polimi.db2.gma.GMAEJB.entities;

import javax.persistence.*;

@Entity
@Table(name = "answer")
public class AnswerEntity {
    @EmbeddedId
    private AnswerEntityPK answerEntityPK;
    private String answer;


    @Column(name = "Answer", nullable = false, length = 45)
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
