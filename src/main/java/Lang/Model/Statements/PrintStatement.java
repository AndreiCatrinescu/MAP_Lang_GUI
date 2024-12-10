package Lang.Model.Statements;

import Lang.Exceptions.InterpreterError;
import Lang.Model.Expressions.Expression;
import Lang.Model.Structures.MyList;
import Lang.Model.Structures.MyTable;
import Lang.Model.Structures.ProgramState;
import Lang.Model.Types.Type;
import Lang.Model.Values.Value;

public class PrintStatement implements Statement {
    private final Expression expression;

    public PrintStatement(Expression exp){
        expression = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterError {
        MyTable<String, Value> symbols = state.getSymTable();
        Value valueToPrint = expression.eval(symbols, state.getHeap());
        MyList<Value> out = state.getOut();
        out.add(valueToPrint);
        return null;
    }

    @Override
    public MyTable<String, Type> typecheck(MyTable<String, Type> typeEnv) throws InterpreterError {
        expression.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString(){
        return "print(" + expression.toString() + ")";
    }
}
