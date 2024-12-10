package Lang.Model.Statements;

import Lang.Exceptions.ExpressionNotString;
import Lang.Exceptions.FileAlreadyOpened;
import Lang.Exceptions.FileDoesNotExist;
import Lang.Exceptions.InterpreterError;
import Lang.Model.Expressions.Expression;
import Lang.Model.Structures.MyTable;
import Lang.Model.Structures.ProgramState;
import Lang.Model.Types.StringType;
import Lang.Model.Types.Type;
import Lang.Model.Values.StringValue;
import Lang.Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenStatement implements Statement {
    private final Expression exp;

    public OpenStatement(Expression path) {
        exp = path;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterError {
        var fileTable = state.getFileTable();
        synchronized (fileTable) {
            Value expValue = exp.eval(state.getSymTable(), state.getHeap());

            if (!expValue.getType().equals(new StringType())) {
                throw new ExpressionNotString(exp);
            }

            StringValue filePath = (StringValue) expValue;

            if (state.getFileTable().isDefined(filePath)) {
                throw new FileAlreadyOpened(filePath.getVal());
            }

            BufferedReader fileDescriptor;
            try {
                fileDescriptor = new BufferedReader(new FileReader(filePath.getVal()));
            } catch (FileNotFoundException e) {
                throw new FileDoesNotExist(filePath.getVal());
            }

            state.getFileTable().put(filePath, fileDescriptor);
        }
        return null;
    }

    @Override
    public MyTable<String, Type> typecheck(MyTable<String, Type> typeEnv) throws InterpreterError {
        Type expType = exp.typecheck(typeEnv);
        if (!expType.equals(new StringType())) {
            throw new ExpressionNotString(exp);
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "open(" + exp.toString() + ")";
    }
}
