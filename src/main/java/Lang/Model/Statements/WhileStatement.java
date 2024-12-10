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

public class WhileStatement implements Statement{
    private final Expression expression;
    private final Statement statement;

    public WhileStatement(Expression exp, Statement stmt) {
        expression = exp;
        statement = stmt;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterError {
        Value expValue = expression.eval(state.getSymTable(), state.getHeap());

        if(!expValue.getType().equals(new BoolType()))
            throw new ExpressionNotBool(expression);

        boolean condition = ((BoolValue)expValue).getVal();

        if (condition) {
            state.getExeStack().push(this);
            state.getExeStack().push(statement);
        }

        return null;
    }

    @Override
    public MyTable<String, Type> typecheck(MyTable<String, Type> typeEnv) throws InterpreterError {
        Type expType = expression.typecheck(typeEnv);
        if (!expType.equals(new BoolType()))
            throw new ExpressionNotBool(expression);
        statement.typecheck(typeEnv.copy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "while(" + expression.toString() + ") {\n" + statement.toString() + "\n}";
    }
}
