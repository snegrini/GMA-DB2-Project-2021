package it.polimi.db2.gma.GMAEJB;

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

    @Column(name = "ExpertiseLevel", nullable = true, length = 45)
    private String expertiseLevel;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getExpertiseLevel() {
        return expertiseLevel;
    }

    public void setExpertiseLevel(String expertiseLevel) {
        this.expertiseLevel = expertiseLevel;
    }
}
