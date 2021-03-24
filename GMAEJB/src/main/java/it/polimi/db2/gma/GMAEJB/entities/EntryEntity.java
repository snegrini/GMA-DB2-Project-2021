package it.polimi.db2.gma.GMAEJB.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "entry")
@NamedQueries({
        @NamedQuery(name = "EntryEntity.getQuestionsAnswers", query = "SELECT NEW it.polimi.db2.gma.GMAEJB.utils.QuestionAnswer(q.question, a.answer) " +
                "FROM EntryEntity e INNER JOIN e.answers a INNER JOIN a.question q WHERE e.questionnaire.id = :qid AND e.user.id = :uid ORDER BY q.id"),
        @NamedQuery(name = "EntryEntity.getStatsAnswers", query = "SELECT NEW it.polimi.db2.gma.GMAEJB.utils.StatsAnswers(s.age, s.sex, s.expertiseLevel) " +
                "FROM EntryEntity e INNER JOIN e.stats s WHERE e.questionnaire.id = :qid AND e.user.id = :uid"),
        @NamedQuery(name = "EntryEntity.findByUserAndQuestionnaire", query = "SELECT e FROM EntryEntity e WHERE e.user.id = :userId AND e.questionnaire.id = :questionnaireId")
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
