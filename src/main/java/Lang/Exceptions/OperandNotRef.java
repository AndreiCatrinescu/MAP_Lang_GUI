package Lang.Exceptions;

import Lang.Model.Expressions.Expression;

public class OperandNotRef extends InterpreterError{
    public OperandNotRef(Expression exp) {
        super(exp.toString() + " Is Not A Reference Type");
    }
}
