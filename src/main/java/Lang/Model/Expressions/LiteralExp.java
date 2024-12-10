package Lang.Model.Expressions;

import Lang.Exceptions.InterpreterError;
import Lang.Model.Structures.IHeap;
import Lang.Model.Structures.MyTable;
import Lang.Model.Types.Type;
import Lang.Model.Values.Value;

public class LiteralExp implements Expression {

    private final Value literalValue;

    public LiteralExp(Value constVal) {
        literalValue = constVal;
    }

    @Override
    public Value eval(MyTable<String, Value> symTable, IHeap hp) throws InterpreterError {
        return literalValue;
    }

    @Override
    public Type typecheck(MyTable<String, Type> typeEnv) throws InterpreterError {
        return literalValue.getType();
    }

    @Override
    public String toString() {
        return "" + literalValue;
    }
}
