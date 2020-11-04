package exception;

public class IncompatibleDrinkTypeOrderException extends  RuntimeException{
    public IncompatibleDrinkTypeOrderException() {
        super("You can't instantiate a cold drink as hot drink");
    }
}
