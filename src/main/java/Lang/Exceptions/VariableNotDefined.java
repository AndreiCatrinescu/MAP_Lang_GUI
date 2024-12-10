package Lang.Exceptions;

public class VariableNotDefined extends InterpreterError {
    public VariableNotDefined(String varName) {
        super("Variable Name " + varName + " Not Defined");
    }
}
