package it.polimi.db2.gma.GMAEJB.entities;

import it.polimi.db2.gma.GMAEJB.enums.ExpertiseLevel;
import it.polimi.db2.gma.GMAEJB.enums.Sex;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "entry")
@NamedQueries({
        @NamedQuery(name = "EntryEntity.getQuestionsAnswers", query = "SELECT NEW it.polimi.db2.gma.GMAEJB.utils.QuestionAnswer(qu.question, a.answer) " +
                "FROM EntryEntity e INNER JOIN e.questionnaire q INNER JOIN e.user u INNER JOIN e.answers a INNER JOIN a.question qu WHERE q.id = :qid AND u.id = :uid ORDER BY qu.id"),
        @NamedQuery(name = "EntryEntity.getStatsAnswers", query = "SELECT NEW it.polimi.db2.gma.GMAEJB.utils.StatsAnswers(s.age, s.sex, s.expertiseLevel) " +
                "FROM EntryEntity e INNER JOIN e.stats s INNER JOIN e.questionnaire q INNER JOIN e.user u WHERE q.id = :qid AND u.id = :uid")
})
public class EntryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int id;

    @Column(name = "Points", nullable = true)
    private Integer points = 0;

    @Column(name = "IsSubmitted", nullable = true)
    private Byte isSubmitted = 0;

    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "QuestionnaireId")
    private QuestionnaireEntity questionnaire;

    @OneToOne(mappedBy = "entry", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private StatsEntity stats;

    @OneToMany(mappedBy = "entry", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<AnswerEntity> answers = new ArrayList<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Byte getIsSubmitted() {
        return isSubmitted;
    }

    public void setIsSubmitted(Byte isSubmitted) {
        this.isSubmitted = isSubmitted;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public QuestionnaireEntity getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(QuestionnaireEntity questionnaire) {
        this.questionnaire = questionnaire;
    }

    public StatsEntity getStats() {
        return stats;
    }

    public void setStats(StatsEntity stats) {
        this.stats = stats;
        stats.setEntry(this);
    }

    public List<AnswerEntity> getAnswers() {
        return answers;
    }

    public void addAnswer(AnswerEntity answer) {
        getAnswers().add(answer);
        answer.setEntry(this);
    }

    public void removeAnswer(AnswerEntity answer) {
        getAnswers().remove(answer);
    }
}
