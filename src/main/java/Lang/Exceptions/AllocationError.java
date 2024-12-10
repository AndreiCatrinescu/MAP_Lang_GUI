package Lang.Exceptions;

public class AllocationError extends InterpreterError {
    public AllocationError(String expectedType, String receivedType) {
        super("Incorrect Type Allocated, Expected: " + expectedType + ", Got: " + receivedType);
    }
}
