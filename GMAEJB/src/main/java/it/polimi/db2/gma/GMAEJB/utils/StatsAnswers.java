package it.polimi.db2.gma.GMAEJB.utils;

import it.polimi.db2.gma.GMAEJB.enums.ExpertiseLevel;
import it.polimi.db2.gma.GMAEJB.enums.Sex;

public class StatsAnswers {
    final private Integer age;
    final private Sex sex;
    final private ExpertiseLevel expertiseLevel;

    public StatsAnswers(Integer age, Sex sex, ExpertiseLevel expertiseLevel) {
        this.age = age;
        this.sex = sex;
        this.expertiseLevel = expertiseLevel;
    }

    public Integer getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }

    public ExpertiseLevel getExpertiseLevel() {
        return expertiseLevel;
    }
}
