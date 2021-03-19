package it.polimi.db2.gma.GMAEJB.exceptions;

public class BadQuestionnaireException extends Exception {
    private static final long serialVersionUID = 1L;

    public BadQuestionnaireException(String message) {
        super(message);
    }
}
