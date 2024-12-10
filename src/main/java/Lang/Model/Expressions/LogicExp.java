package Lang.Model.Expressions;

import Lang.Exceptions.OperandNotBool;
import Lang.Exceptions.InterpreterError;
import Lang.Model.Structures.IHeap;
import Lang.Model.Structures.MyTable;
import Lang.Model.Types.BoolType;
import Lang.Model.Types.Type;
import Lang.Model.Values.BoolValue;
import Lang.Model.Values.Value;

public class LogicExp implements Expression {
    private final Expression left;
    private final Expression right;
    private final LogicOperation sign;

    public LogicExp(Expression l, Expression r, LogicOperation s) {
        left = l;
        right = r;
        sign = s;
    }

    @Override
    public Value eval(MyTable<String, Value> symTable, IHeap hp) throws InterpreterError {
        Value firstOperand = left.eval(symTable, hp);
        if (!firstOperand.getType().equals(new BoolType()))
            throw new OperandNotBool(left);

        Value secondOperand = right.eval(symTable, hp);
        if (!secondOperand.getType().equals(new BoolType()))
            throw new OperandNotBool(right);

        boolean firstValue = ((BoolValue) firstOperand).getVal();
        boolean secondValue = ((BoolValue) secondOperand).getVal();

        return switch (sign){
            case And -> new BoolValue(firstValue && secondValue);
            case Or -> new BoolValue(firstValue || secondValue);
        };
    }

    @Override
    public Type typecheck(MyTable<String, Type> typeEnv) throws InterpreterError {
        Type leftType = left.typecheck(typeEnv);
        Type rightType = right.typecheck(typeEnv);
        if (!leftType.equals(new BoolType()))
            throw new OperandNotBool(left);
        if (!rightType.equals(new BoolType()))
            throw new OperandNotBool(right);
        return new BoolType();
    }

    @Override
    public String toString() {
        String operationSymbol = switch (sign) {
            case And -> " && ";
            case Or -> " || ";
        };

        return left + operationSymbol + right;
    }
}
