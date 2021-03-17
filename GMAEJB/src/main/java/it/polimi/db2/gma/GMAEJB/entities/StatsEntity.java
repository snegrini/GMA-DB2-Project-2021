package it.polimi.db2.gma.GMAEJB.entities;

import it.polimi.db2.gma.GMAEJB.enums.ExpertiseLevel;
import it.polimi.db2.gma.GMAEJB.enums.Sex;

import javax.persistence.*;

@Entity
@Table(name = "stats")
public class StatsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int id;

    @Column(name = "Age", nullable = true)
    private Integer age;

    @Column(name = "Sex", nullable = true)
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(name = "ExpertiseLevel", nullable = true)
    @Enumerated(EnumType.STRING)
    private ExpertiseLevel expertiseLevel;

    @ManyToOne
    @JoinColumn(name = "EntryId")
    private EntryEntity entry;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public ExpertiseLevel getExpertiseLevel() {
        return expertiseLevel;
    }

    public void setExpertiseLevel(ExpertiseLevel expertiseLevel) {
        this.expertiseLevel = expertiseLevel;
    }
}
