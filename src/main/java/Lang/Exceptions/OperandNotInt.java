package Lang.Exceptions;

import Lang.Model.Expressions.Expression;

public class OperandNotInt extends InterpreterError {
    public OperandNotInt(Expression exp) {
        super(exp.toString() + " Is Not An Int");
    }
}
