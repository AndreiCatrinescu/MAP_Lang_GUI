package Lang.Model.Statements;

import Lang.Exceptions.InterpreterError;
import Lang.Exceptions.VariableNotDefined;
import Lang.Exceptions.WrongTypeAssign;
import Lang.Model.Expressions.Expression;
import Lang.Model.Structures.MyTable;
import Lang.Model.Structures.ProgramState;
import Lang.Model.Types.Type;
import Lang.Model.Values.Value;

public class AssignStatement implements Statement {
    private final String varName;
    private final Expression expression;

    public AssignStatement(String name, Expression exp) {
        varName = name;
        expression = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterError {
        MyTable<String, Value> symTable = state.getSymTable();
        if (!symTable.isDefined(varName))
            throw new VariableNotDefined(varName);
        Value expValue;
        synchronized (state.getHeap()) {
            expValue = expression.eval(symTable, state.getHeap());
        }
        Type varType = symTable.get(varName).getType();

        if (!expValue.getType().equals(varType))
            throw new WrongTypeAssign(varType.toString(), expValue.getType().toString());

        symTable.put(varName, expValue);

        return null;
    }

    @Override
    public MyTable<String, Type> typecheck(MyTable<String, Type> typeEnv) throws InterpreterError {
        Type varType = typeEnv.get(varName);
        if (varType == null)
            throw new VariableNotDefined(varName);
        Type expType = expression.typecheck(typeEnv);
        if(!varType.equals(expType))
            throw new WrongTypeAssign(varType.toString(), expType.toString());
        return typeEnv;
    }

    @Override
    public String toString() {
        return varName + " = " + expression.toString() ;
    }
}
