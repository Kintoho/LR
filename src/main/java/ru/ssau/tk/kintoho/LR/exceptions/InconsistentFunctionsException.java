package ru.ssau.tk.kintoho.LR.exceptions;

public class InconsistentFunctionsException extends RuntimeException {
    private static final long serialVersionUID = 1269184723135709239L;

    public InconsistentFunctionsException() {
        super();
    }

    public InconsistentFunctionsException(String message) {
        super(message);
    }
}
