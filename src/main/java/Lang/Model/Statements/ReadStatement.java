package Lang.Model.Statements;

import Lang.Exceptions.*;
import Lang.Model.Expressions.Expression;
import Lang.Model.Expressions.VariableExp;
import Lang.Model.Structures.MyTable;
import Lang.Model.Structures.ProgramState;
import Lang.Model.Types.IntType;
import Lang.Model.Types.StringType;
import Lang.Model.Types.Type;
import Lang.Model.Values.IntValue;
import Lang.Model.Values.StringValue;
import Lang.Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadStatement implements Statement {
    private final String varName;
    private final Expression exp;

    public ReadStatement(Expression path, String buffer) {
        exp = path;
        varName = buffer;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterError {
        if (!state.getSymTable().isDefined(varName)) {
            throw new VariableNotDefined(varName);
        }

        Value expValue = exp.eval(state.getSymTable(), state.getHeap());

        if (!expValue.getType().equals(new StringType())) {
            throw new ExpressionNotString(exp);
        }

        if (!state.getSymTable().get(varName).getType().equals(new IntType())) {
            throw new OperandNotInt(new VariableExp(varName));
        }

        StringValue filePath = (StringValue) expValue;
        var fileTable = state.getFileTable();
        synchronized (fileTable) {
            if (!state.getFileTable().isDefined(filePath)) {
                throw new FileNotOpened(filePath.getVal());
            }

            BufferedReader fileDescriptor = state.getFileTable().get(filePath);
            String line;
            try {
                line = fileDescriptor.readLine();
            } catch (IOException e) {
                throw new IOError("Error When Reading From File");
            }

            int value = (line == null) ? 0 : Integer.parseInt(line);

            state.getSymTable().put(varName, new IntValue(value));
        }
        return null;
    }

    @Override
    public MyTable<String, Type> typecheck(MyTable<String, Type> typeEnv) throws InterpreterError {
        Type varType = typeEnv.get(varName);
        if (varType == null)
            throw new VariableNotDefined(varName);
        Type expType = exp.typecheck(typeEnv);
        if (!expType.equals(new StringType())) {
            throw new ExpressionNotString(exp);
        }
        if (!varType.equals(new IntType())) {
            throw new OperandNotInt(new VariableExp(varName));
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "read(" + exp.toString() + ", " + varName + ")";
    }
}
