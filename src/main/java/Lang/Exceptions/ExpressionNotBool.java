package Lang.Exceptions;

import Lang.Model.Expressions.Expression;

public class ExpressionNotBool extends InterpreterError {
    public ExpressionNotBool(Expression exp) {
        super("Expression" + exp + "Does Not Evaluate To A Boolean");
    }
}
