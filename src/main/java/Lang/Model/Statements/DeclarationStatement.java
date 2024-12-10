package Lang.Model.Statements;

import Lang.Exceptions.DoubleDeclaration;
import Lang.Exceptions.InterpreterError;
import Lang.Model.Structures.MyTable;
import Lang.Model.Structures.ProgramState;
import Lang.Model.Types.Type;

public class DeclarationStatement implements Statement {
    private final String varName;
    private final Type varType;

    public DeclarationStatement(String name, Type type){
        varName = name;
        varType = type;
    }


    @Override
    public ProgramState execute(ProgramState state) throws InterpreterError {
        if(state.getSymTable().isDefined(varName))
            throw new DoubleDeclaration(varName);

        state.getSymTable().put(varName, varType.getDefaultValue());

        return null;
    }

    @Override
    public MyTable<String, Type> typecheck(MyTable<String, Type> typeEnv) throws InterpreterError {
        if (typeEnv.isDefined(varName))
            throw new DoubleDeclaration(varName);
        typeEnv.put(varName, varType);
        return typeEnv;
    }

    @Override
    public String toString() {
        return varType.toString() + " " + varName;
    }
}
