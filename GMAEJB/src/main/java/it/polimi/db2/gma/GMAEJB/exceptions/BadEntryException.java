package it.polimi.db2.gma.GMAEJB.exceptions;

public class BadEntryException extends Exception {
    private static final long serialVersionUID = 1L;

    public BadEntryException(String message) {
        super(message);
    }
}
