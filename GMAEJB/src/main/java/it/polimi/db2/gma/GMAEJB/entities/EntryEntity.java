package it.polimi.db2.gma.GMAEJB.entities;

import it.polimi.db2.gma.GMAEJB.enums.ExpertiseLevel;
import it.polimi.db2.gma.GMAEJB.enums.Sex;

import javax.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    private UserEntity user;

    @Column(name = "Points", nullable = true)
    private Integer points;

    @Column(name = "IsSubmitted", nullable = true)
    private Byte isSubmitted;

    @ManyToOne
    @JoinColumn(name = "QuestionnaireId")
    private QuestionnaireEntity questionnaire;

    @OneToMany(mappedBy = "entry", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<StatsEntity> stats;

    @OneToMany(mappedBy = "entry", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<AnswerEntity> answers;


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

    public UserEntity getUser() {
        return user;
    }

    public Byte getIsSubmitted() {
        return isSubmitted;
    }

    public void setIsSubmitted(Byte isSubmitted) {
        this.isSubmitted = isSubmitted;
    }

    public void setUserEntity(UserEntity user) {
        this.user = user;
    }

    public void setQuestionnaireEntity(QuestionnaireEntity questionnaire) {
        this.questionnaire = questionnaire;
    }

    public void addAnswerEntity(String answer) {
        AnswerEntity a = new AnswerEntity();
        a.setAnswer(answer);
        this.answers.add(a);
    }

    public void addStatsEntity(String age, String sex, String eLevel) {
        StatsEntity s = new StatsEntity();
        s.setAge(Integer.valueOf(age));
        s.setExpertiseLevel(ExpertiseLevel.valueOf(eLevel));
        s.setSex(Sex.valueOf(sex));
        this.stats.add(s);
    }
}
