package it.polimi.db2.gma.GMAEJB.entities;

import it.polimi.db2.gma.GMAEJB.enums.ExpertiseLevel;

import javax.persistence.*;

@Entity
@Table(name = "stats")
public class StatsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int id;

    @Column(name = "Age", nullable = true, length = 45)
    private String age;

    @Column(name = "Sex", nullable = true, length = 45)
    private String sex;

    @Column(name = "ExpertiseLevel", nullable = true)
    @Enumerated(EnumType.STRING)
    private ExpertiseLevel expertiseLevel;

    @ManyToOne
    @JoinColumn(name = "EntryId")
    private EntryEntity entry;


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public ExpertiseLevel getExpertiseLevel() {
        return expertiseLevel;
    }

    public void setExpertiseLevel(ExpertiseLevel expertiseLevel) {
        this.expertiseLevel = expertiseLevel;
    }
}
