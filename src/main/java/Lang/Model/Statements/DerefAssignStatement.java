package Lang.Model.Statements;

import Lang.Exceptions.*;
import Lang.Model.Expressions.Expression;
import Lang.Model.Expressions.VariableExp;
import Lang.Model.Structures.IHeap;
import Lang.Model.Structures.MyTable;
import Lang.Model.Structures.ProgramState;
import Lang.Model.Types.ReferenceType;
import Lang.Model.Types.Type;
import Lang.Model.Values.ReferenceValue;
import Lang.Model.Values.Value;


public class DerefAssignStatement implements Statement{
    private final String varName;
    private final Expression expression;

    public DerefAssignStatement(String var, Expression exp) {
        varName = var;
        expression = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterError {
        IHeap heap = state.getHeap();
        synchronized (heap) {
            if (!state.getSymTable().isDefined(varName))
                throw new VariableNotDefined(varName);

            Value expValue = expression.eval(state.getSymTable(), heap);
            Value varValue = state.getSymTable().get(varName);

            if (!(varValue.getType() instanceof ReferenceType))
                throw new OperandNotRef(new VariableExp(varName));

            if (!varValue.getType().equals(new ReferenceType(expValue.getType())))
                throw new WrongTypeAssign(varValue.getType().toString(), expValue.getType().toString());

            Integer address = ((ReferenceValue) varValue).getAddress();

            if (!state.getHeap().isAllocated(address))
                throw new MemoryViolation(address);

            state.getHeap().put(address, expValue);
        }
        return null;
    }

    @Override
    public MyTable<String, Type> typecheck(MyTable<String, Type> typeEnv) throws InterpreterError {
        Type varType = typeEnv.get(varName);
        if (varType == null)
            throw new VariableNotDefined(varName);
        if (!(varType instanceof ReferenceType))
                throw new OperandNotRef(new VariableExp(varName));
        Type expType = expression.typecheck(typeEnv);
        if (!varType.equals(new ReferenceType(expType)))
                throw new WrongTypeAssign(varType.toString(), expType.toString());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "*" + varName + " = " + expression.toString();
    }
}
