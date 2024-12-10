package Lang.Model.Statements;

import Lang.Exceptions.InterpreterError;
import Lang.Model.Structures.ExecutionStack;
import Lang.Model.Structures.MyTable;
import Lang.Model.Structures.ProgramState;
import Lang.Model.Types.Type;

public class ThreadStatement implements Statement {
    private final Statement statement;


    public ThreadStatement(Statement stmt) {
        statement = stmt;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterError {
        synchronized (state.getHeap()) {
            return new ProgramState(
                    statement,
                    new ExecutionStack<>(),
                    state.getOut(),
                    state.getSymTable().copy(),
                    state.getFileTable(),
                    state.getHeap()
            );
        }
    }

    @Override
    public MyTable<String, Type> typecheck(MyTable<String, Type> typeEnv) throws InterpreterError {
        statement.typecheck(typeEnv.copy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "thread {\n" + statement + "\n}";
    }
}
