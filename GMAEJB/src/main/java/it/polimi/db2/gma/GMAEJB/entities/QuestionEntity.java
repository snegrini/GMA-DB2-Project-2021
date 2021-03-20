package it.polimi.db2.gma.GMAEJB.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "question")
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int id;

    @Column(name = "Question", nullable = false, length = 200)
    private String question;

    @ManyToOne
    @JoinColumn(name = "QuestionnaireId")
    private QuestionnaireEntity questionnaire;

    @OneToMany(mappedBy = "question", cascade = { CascadeType.REMOVE }, orphanRemoval = true)
    private List<AnswerEntity> answers;

    public QuestionEntity(String question) {
        this.question = question;
    }

    public QuestionEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public QuestionnaireEntity getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(QuestionnaireEntity questionnaire) {
        this.questionnaire = questionnaire;
    }

    public List<AnswerEntity> getAnswers() {
        return answers;
    }
}
