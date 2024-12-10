package Lang.Exceptions;

import Lang.Model.Expressions.Expression;

public class ExpressionNotString extends InterpreterError{
    public ExpressionNotString(Expression exp) {
        super("Expression" + exp + "Does Not Evaluate To A String");
    }
}
