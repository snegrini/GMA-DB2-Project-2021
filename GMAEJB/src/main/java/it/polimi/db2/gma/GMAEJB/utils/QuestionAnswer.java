package it.polimi.db2.gma.GMAEJB.utils;

public class QuestionAnswer {
    final private String question;
    final private String answer;

    public QuestionAnswer(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
