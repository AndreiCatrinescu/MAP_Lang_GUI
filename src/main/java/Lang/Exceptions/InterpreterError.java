package Lang.Exceptions;

public class InterpreterError extends RuntimeException {
    public InterpreterError(String message) {
        super(message);
    }
}
