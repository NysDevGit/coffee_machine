package model.exception;

public class TooMuchSugarException extends RuntimeException{

    public TooMuchSugarException(String error){
        super(error);
    }
}
