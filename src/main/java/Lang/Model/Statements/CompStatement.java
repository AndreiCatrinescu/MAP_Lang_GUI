package Lang.Model.Statements;

import Lang.Exceptions.InterpreterError;
import Lang.Model.Structures.MyStack;
import Lang.Model.Structures.MyTable;
import Lang.Model.Structures.ProgramState;
import Lang.Model.Types.Type;

public class CompStatement implements Statement {
    public Statement getFirst() {
        return first;
    }

    public Statement getLast() {
        return last;
    }

    private final Statement first;
    private final Statement last;

    public CompStatement(Statement f, Statement l){
        first = f;
        last = l;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterError {
        MyStack<Statement> newStack = state.getExeStack();
        newStack.push(last);
        newStack.push(first);
        return null;
    }

    @Override
    public MyTable<String, Type> typecheck(MyTable<String, Type> typeEnv) throws InterpreterError {
        return last.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public String toString() {
        return first.toString() + '\n' + last.toString();
    }

}
