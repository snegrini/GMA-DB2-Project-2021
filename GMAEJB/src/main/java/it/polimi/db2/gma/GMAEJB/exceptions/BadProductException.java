package it.polimi.db2.gma.GMAEJB.exceptions;

public class BadProductException extends Exception {
    private static final long serialVersionUID = 1L;

    public BadProductException(String message) {
        super(message);
    }
}
