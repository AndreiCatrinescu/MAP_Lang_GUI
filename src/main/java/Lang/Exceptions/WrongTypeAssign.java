package Lang.Exceptions;

public class WrongTypeAssign extends InterpreterError {
    public WrongTypeAssign(String expectedType, String receivedType) {
        super("Incorrect Type Assigned, Expected: " + expectedType + ", Got: " + receivedType);
    }
}
