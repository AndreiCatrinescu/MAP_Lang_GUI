package Lang.Model.Statements;

import Lang.Exceptions.ExpressionNotBool;
import Lang.Exceptions.InterpreterError;
import Lang.Model.Expressions.Expression;
import Lang.Model.Structures.MyTable;
import Lang.Model.Structures.ProgramState;
import Lang.Model.Types.BoolType;
import Lang.Model.Types.Type;
import Lang.Model.Values.BoolValue;
import Lang.Model.Values.Value;

public class IfStatement implements Statement {
    private final Expression expression;
    private final Statement trueBranch;
    private final Statement falseBranch;

    public IfStatement(Expression exp, Statement trueBranch, Statement falseBranch) {
        expression = exp;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    @Override
    public String toString() {
        return "if(" + expression.toString() + ") {\n" + trueBranch.toString() + "\n} else {\n" + falseBranch.toString() + "\n}";
    }

    public ProgramState execute(ProgramState state) throws InterpreterError {
        Value expressionValue;
        synchronized (state.getHeap()) {
            expressionValue = expression.eval(state.getSymTable(), state.getHeap());
        }
        if (!expressionValue.getType().equals(new BoolType()))
            throw new ExpressionNotBool(expression);

        if (((BoolValue) expressionValue).getVal())
            state.getExeStack().push(trueBranch);
        else state.getExeStack().push(falseBranch);

        return null;
    }

    @Override
    public MyTable<String, Type> typecheck(MyTable<String, Type> typeEnv) throws InterpreterError {
        Type expType = expression.typecheck(typeEnv);
        if (!expType.equals(new BoolType()))
            throw new ExpressionNotBool(expression);
        trueBranch.typecheck(typeEnv.copy());
        falseBranch.typecheck(typeEnv.copy());
        return typeEnv;
    }
}
