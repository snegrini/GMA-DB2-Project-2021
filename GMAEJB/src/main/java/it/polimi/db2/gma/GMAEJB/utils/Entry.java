package it.polimi.db2.gma.GMAEJB.utils;

import java.util.List;

public class Entry {
    final private String username;
    final private StatsAnswers statsAnswers;
    final private List<QuestionAnswer> answerList;

    public Entry(String username, StatsAnswers statsAnswers, List<QuestionAnswer> answerList) {
        this.username = username;
        this.statsAnswers = statsAnswers;
        this.answerList = answerList;
    }

    public String getUsername() {
        return username;
    }

    public StatsAnswers getStatsAnswers() {
        return statsAnswers;
    }

    public List<QuestionAnswer> getAnswerList() {
        return answerList;
    }
}
