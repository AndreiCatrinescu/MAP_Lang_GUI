package Lang.Model.Expressions;

import Lang.Exceptions.InterpreterError;
import Lang.Exceptions.OperandNotInt;
import Lang.Model.Structures.IHeap;
import Lang.Model.Structures.MyTable;
import Lang.Model.Types.BoolType;
import Lang.Model.Types.IntType;
import Lang.Model.Types.Type;
import Lang.Model.Values.BoolValue;
import Lang.Model.Values.IntValue;
import Lang.Model.Values.Value;

public class CompareIntExp implements Expression {
    private final Expression left;
    private final Expression right;
    private final CompOperator operator;

    public CompareIntExp(Expression l, Expression r, CompOperator op) {
        left = l;
        right = r;
        operator = op;
    }

    @Override
    public Value eval(MyTable<String, Value> symTable, IHeap hp) throws InterpreterError {
        Value firstOperand = left.eval(symTable, hp);
        if (!firstOperand.getType().equals(new IntType()))
            throw new OperandNotInt(left);

        Value secondOperand = right.eval(symTable, hp);
        if (!secondOperand.getType().equals(new IntType()))
            throw new OperandNotInt(right);

        int firstValue = ((IntValue) firstOperand).getVal();
        int secondValue = ((IntValue) secondOperand).getVal();

        return switch (operator) {
            case Equal -> new BoolValue(firstValue == secondValue);
            case Greater -> new BoolValue(firstValue > secondValue);
            case GreaterOrEqual -> new BoolValue(firstValue >= secondValue);
            case Lesser -> new BoolValue(firstValue < secondValue);
            case LesserOrEqual -> new BoolValue(firstValue <= secondValue);
            case NotEqual -> new BoolValue(firstValue != secondValue);
        };
    }

    @Override
    public Type typecheck(MyTable<String, Type> typeEnv) throws InterpreterError {
        Type leftType = left.typecheck(typeEnv);
        Type rightType = right.typecheck(typeEnv);
        if (!leftType.equals(new IntType()))
            throw new OperandNotInt(left);
        if (!rightType.equals(new IntType()))
            throw new OperandNotInt(right);
        return new BoolType();
    }

    @Override
    public String toString() {
        String operationSymbol = switch (operator) {
            case Equal -> " == ";
            case Greater -> " > ";
            case GreaterOrEqual -> " >= ";
            case Lesser -> " < ";
            case LesserOrEqual -> " <= ";
            case NotEqual -> " != ";
        };

        return left.toString() + operationSymbol + right.toString();
    }
}
