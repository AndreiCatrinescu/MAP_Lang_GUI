package Lang.Model.Statements;

import Lang.Exceptions.InterpreterError;
import Lang.Model.Structures.MyTable;
import Lang.Model.Structures.ProgramState;
import Lang.Model.Types.Type;

public class NopStatement implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws InterpreterError {
        return null;
    }

    @Override
    public MyTable<String, Type> typecheck(MyTable<String, Type> typeEnv) throws InterpreterError {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "nop";
    }
}
