package Lang.Model.Statements;

import Lang.Exceptions.ExpressionNotString;
import Lang.Exceptions.FileNotOpened;
import Lang.Exceptions.IOError;
import Lang.Exceptions.InterpreterError;
import Lang.Model.Expressions.Expression;
import Lang.Model.Structures.MyTable;
import Lang.Model.Structures.ProgramState;
import Lang.Model.Types.StringType;
import Lang.Model.Types.Type;
import Lang.Model.Values.StringValue;
import Lang.Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseStatement implements Statement {
    private final Expression expression;

    public CloseStatement(Expression path) {
        expression = path;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterError {
        var fileTable = state.getFileTable();
        synchronized (fileTable) {
            Value expValue = expression.eval(state.getSymTable(), state.getHeap());

            if (!expValue.getType().equals(new StringType())) {
                throw new ExpressionNotString(expression);
            }

            StringValue filePath = (StringValue) expValue;

            if (!fileTable.isDefined(filePath)) {
                throw new FileNotOpened(filePath.getVal());
            }

            BufferedReader fileDescriptor = fileTable.get(filePath);
            try {
                fileDescriptor.close();
            } catch (IOException e) {
                throw new IOError("Error Closing The File");
            }

            fileTable.remove(filePath);
        }
        return null;
    }

    @Override
    public MyTable<String, Type> typecheck(MyTable<String, Type> typeEnv) throws InterpreterError {
        Type expType = expression.typecheck(typeEnv);
        if (!expType.equals(new StringType()))
            throw new ExpressionNotString(expression);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "close(" + expression.toString() + ")";
    }
}
