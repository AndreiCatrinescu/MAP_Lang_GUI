package Lang.Model.Statements;

import Lang.Exceptions.InterpreterError;
import Lang.Model.Structures.MyTable;
import Lang.Model.Structures.ProgramState;
import Lang.Model.Types.Type;

public interface Statement {
    ProgramState execute(ProgramState state) throws InterpreterError;
    MyTable<String,Type> typecheck(MyTable<String, Type> typeEnv) throws InterpreterError;
}
