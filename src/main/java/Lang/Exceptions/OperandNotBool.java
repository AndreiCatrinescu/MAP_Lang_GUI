package Lang.Exceptions;

import Lang.Model.Expressions.Expression;

public class OperandNotBool extends InterpreterError {
    public OperandNotBool(Expression exp) {
        super(exp.toString() + " Is Not A Bool");
    }
}
