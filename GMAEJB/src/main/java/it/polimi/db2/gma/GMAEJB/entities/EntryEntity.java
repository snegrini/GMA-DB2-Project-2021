package it.polimi.db2.gma.GMAEJB.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "entry")
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
}
