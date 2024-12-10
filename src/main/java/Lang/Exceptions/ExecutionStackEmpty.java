package Lang.Exceptions;

public class ExecutionStackEmpty extends InterpreterError {
    public ExecutionStackEmpty() {
        super("Cannot Execute Empty Program");
    }
}
