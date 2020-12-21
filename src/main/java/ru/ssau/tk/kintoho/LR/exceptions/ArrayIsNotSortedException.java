package ru.ssau.tk.kintoho.LR.exceptions;

public class ArrayIsNotSortedException extends RuntimeException {
    private static final long serialVersionUID = -41375798775944294L;

    public ArrayIsNotSortedException() {
        super();
    }

    public ArrayIsNotSortedException(String message) {
        super(message);
    }
}
