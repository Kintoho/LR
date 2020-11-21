package ru.ssau.tk.kintoho.LR.exceptions;

public class InterpolationException extends RuntimeException{
    public InterpolationException(){
        super();
    }

    public InterpolationException (String message){
        super(message);
    }
}
