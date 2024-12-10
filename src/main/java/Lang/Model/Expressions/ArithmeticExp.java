package Lang.Model.Expressions;

import Lang.Exceptions.DivisionByZero;
import Lang.Exceptions.OperandNotInt;
import Lang.Exceptions.InterpreterError;
import Lang.Model.Structures.IHeap;
import Lang.Model.Structures.MyTable;
import Lang.Model.Types.IntType;
import Lang.Model.Types.Type;
import Lang.Model.Values.IntValue;
import Lang.Model.Values.Value;

public class ArithmeticExp implements Expression {
    private final Expression left;
    private final Expression right;
    private final IntOperation operation;

    public ArithmeticExp(Expression l, Expression r, IntOperation op){
        left = l;
        right = r;
        operation = op;
    }

    @Override
    public String toString() {
        String operationSymbol = switch (operation){
            case Add -> " + ";
            case Div -> " / ";
            case Mul -> " * ";
            case Sub -> " - ";
        };

        return left.toString() + operationSymbol + right.toString();
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

        return switch (operation) {
            case Add -> new IntValue(firstValue + secondValue);
            case Sub -> new IntValue(firstValue - secondValue);
            case Mul -> new IntValue(firstValue * secondValue);
            case Div -> {
                if (secondValue == 0)
                    throw new DivisionByZero();
                yield new IntValue(firstValue / secondValue);
            }
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
        return new IntType();
    }
}
