package Lang.Model.Expressions;

import Lang.Exceptions.InterpreterError;
import Lang.Model.Structures.IHeap;
import Lang.Model.Structures.MyTable;
import Lang.Model.Types.Type;
import Lang.Model.Values.Value;

public interface Expression {
    Value eval(MyTable<String, Value> symTable, IHeap hp) throws InterpreterError;
    Type typecheck(MyTable<String,Type> typeEnv) throws InterpreterError;
}
