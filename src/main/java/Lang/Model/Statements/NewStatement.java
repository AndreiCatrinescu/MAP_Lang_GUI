package Lang.Model.Statements;

import Lang.Exceptions.AllocationError;
import Lang.Exceptions.InterpreterError;
import Lang.Exceptions.OperandNotRef;
import Lang.Exceptions.VariableNotDefined;
import Lang.Model.Expressions.Expression;
import Lang.Model.Expressions.VariableExp;
import Lang.Model.Structures.IHeap;
import Lang.Model.Structures.MyTable;
import Lang.Model.Structures.ProgramState;
import Lang.Model.Types.ReferenceType;
import Lang.Model.Types.Type;
import Lang.Model.Values.ReferenceValue;
import Lang.Model.Values.Value;

public class NewStatement implements Statement {
    private final String varName;
    private final Expression expression;

    public NewStatement(String name, Expression exp) {
        varName = name;
        expression = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterError {
        IHeap heap = state.getHeap();
        synchronized (heap) {
            if (!state.getSymTable().isDefined(varName))
                throw new VariableNotDefined(varName);

            Value expValue = expression.eval(state.getSymTable(), heap);
            Type varType = state.getSymTable().get(varName).getType();

            if (!(varType instanceof ReferenceType))
                throw new OperandNotRef(new VariableExp(varName));

            if (!varType.equals(new ReferenceType(expValue.getType())))
                throw new AllocationError(((ReferenceType) varType).getInnerType().toString(), expValue.getType().toString());

            state.getSymTable().put(varName, new ReferenceValue(heap.insert(expValue), expValue.getType()));
        }
        return null;
    }

    @Override
    public MyTable<String, Type> typecheck(MyTable<String, Type> typeEnv) throws InterpreterError {
        Type varType = typeEnv.get(varName);
        if (varType == null)
            throw new VariableNotDefined(varName);
        Type expType = expression.typecheck(typeEnv);
        if (!(varType instanceof ReferenceType))
                throw new OperandNotRef(new VariableExp(varName));
        if(!varType.equals(new ReferenceType(expType)))
            throw new AllocationError(((ReferenceType) varType).getInnerType().toString(), expType.toString());
        return typeEnv;
    }

    @Override
    public String toString() {
        return varName + " = new(" + expression.toString() + ")";
    }
}
