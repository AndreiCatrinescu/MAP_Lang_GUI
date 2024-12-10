package Lang.Model.Expressions;

import Lang.Exceptions.InterpreterError;
import Lang.Exceptions.MemoryViolation;
import Lang.Exceptions.OperandNotRef;
import Lang.Model.Structures.IHeap;
import Lang.Model.Structures.MyTable;
import Lang.Model.Types.ReferenceType;
import Lang.Model.Types.Type;
import Lang.Model.Values.ReferenceValue;
import Lang.Model.Values.Value;

public class DerefExp implements Expression {
    private final Expression expression;

    public DerefExp(Expression exp) {
        expression = exp;
    }

    @Override
    public Value eval(MyTable<String, Value> symTable, IHeap hp) throws InterpreterError {
        Value expValue = expression.eval(symTable, hp);

        if (!(expValue instanceof ReferenceValue))
            throw new OperandNotRef(expression);

        Integer address = ((ReferenceValue) expValue).getAddress();
        if (!hp.isAllocated(address))
            throw new MemoryViolation(address);

        return hp.get(address);
    }

    @Override
    public Type typecheck(MyTable<String, Type> typeEnv) throws InterpreterError {
        Type expType = expression.typecheck(typeEnv);
        if(!(expType instanceof ReferenceType))
            throw new OperandNotRef(expression);
        return ((ReferenceType)expType).getInnerType();
    }

    @Override
    public String toString() {
        return "*" + expression.toString();
    }
}
