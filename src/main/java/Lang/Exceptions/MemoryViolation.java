package Lang.Exceptions;

public class MemoryViolation extends InterpreterError{
    public MemoryViolation(Integer address) {
        super("Access At Address" + address + "Not Allowed");
    }
}
