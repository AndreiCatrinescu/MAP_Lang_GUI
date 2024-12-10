package Lang.Model.Expressions;

import Lang.Exceptions.InterpreterError;
import Lang.Exceptions.VariableNotDefined;
import Lang.Model.Structures.IHeap;
import Lang.Model.Structures.MyTable;
import Lang.Model.Types.Type;
import Lang.Model.Values.Value;


public class VariableExp implements Expression {
    private final String variableName;

    public VariableExp(String name){
        variableName = name;
    }

    @Override
    public Value eval(MyTable<String, Value> symTable, IHeap hp) throws InterpreterError {
        if (!symTable.isDefined(variableName))
            throw new VariableNotDefined(variableName);
        return symTable.get(variableName);
    }

    @Override
    public Type typecheck(MyTable<String, Type> typeEnv) throws InterpreterError {
        Type varType = typeEnv.get(variableName);
        if (varType == null)
            throw new VariableNotDefined(variableName);
        return varType;
    }

    @Override
    public String toString() {
        return variableName;
    }
}
