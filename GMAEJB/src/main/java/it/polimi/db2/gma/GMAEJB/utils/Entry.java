package it.polimi.db2.gma.GMAEJB.utils;

import java.util.List;

public class Entry {
    final private StatsAnswers statsAnswers;
    final private List<QuestionAnswer> questionAnswerList;

    public Entry(StatsAnswers statsAnswers, List<QuestionAnswer> questionAnswerList) {
        this.statsAnswers = statsAnswers;
        this.questionAnswerList = questionAnswerList;
    }

    public StatsAnswers getStatsAnswer() {
        return statsAnswers;
    }

    public List<QuestionAnswer> getQuestionAnswerList() {
        return questionAnswerList;
    }
}
