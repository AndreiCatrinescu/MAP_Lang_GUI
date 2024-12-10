package Lang.Exceptions;

public class DoubleDeclaration extends InterpreterError {
    public DoubleDeclaration(String varName) {
        super("Variable " + varName + " Already Declared");
    }
}
