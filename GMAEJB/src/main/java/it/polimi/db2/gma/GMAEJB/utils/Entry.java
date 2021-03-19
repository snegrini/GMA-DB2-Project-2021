package it.polimi.db2.gma.GMAEJB.utils;

import java.util.List;

public class Entry {
    final private StatsAnswers statsAnswers;
    final private List<QuestionAnswer> answerList;

    public Entry(StatsAnswers statsAnswers, List<QuestionAnswer> answerList) {
        this.statsAnswers = statsAnswers;
        this.answerList = answerList;
    }

    public StatsAnswers getStatsAnswers() {
        return statsAnswers;
    }

    public List<QuestionAnswer> getAnswerList() {
        return answerList;
    }
}
