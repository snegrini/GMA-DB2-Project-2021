package it.polimi.db2.gma.GMAEJB.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
@NamedQueries({
        @NamedQuery(name = "QuestionEntity.findAllByQuestionnaire", query = "SELECT q FROM QuestionEntity q WHERE q.id = :questionnaireId")
})
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

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<AnswerEntity> answers = new ArrayList<>();

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

    public void addAnswer(AnswerEntity answer) {
        getAnswers().add(answer);
        answer.setQuestion(this);
    }

    public void removeAnswer(AnswerEntity answer) {
        getAnswers().remove(answer);
    }
}
