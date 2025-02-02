package Lang.View;

import Lang.Controller.Controller;
import Lang.Exceptions.InterpreterError;
import Lang.Model.Statements.Statement;
import Lang.Model.Structures.*;
import Lang.Model.Values.StringValue;
import Lang.Model.Values.Value;
import Lang.Repo.ProgramRepo;
import Lang.Repo.Repository;

import java.io.BufferedReader;
import java.util.List;

public class RunExample extends Command {
    private final Statement statement;
    private Controller controller;

    public List<ProgramState> getProgramStates() {
        return controller.getStates();
    }

    public void typecheck() throws InterpreterError {
        statement.typecheck(new SymbolTable<>());
    }

    private static Controller makeController(Statement stmt) throws InterpreterError {
        stmt.typecheck(new SymbolTable<>());
        MyStack<Statement> exeStack = new ExecutionStack<>();
        MyList<Value> out = new Out<>();
        MyTable<String, Value> symbolTable = new SymbolTable<>();
        MyTable<StringValue, BufferedReader> fileTable = new FileTable<>();
        IHeap heap = new Heap();
        ProgramState programState1 = new ProgramState(stmt, exeStack, out, symbolTable, fileTable, heap);

        Repository repo = new ProgramRepo(programState1);

        return new Controller(repo);
    }

    public RunExample(String key, String desc, Statement stmt) throws InterpreterError {
        super(key, desc);
        statement = stmt;
        controller = makeController(statement);
    }

    @Override
    public void execute() {
        try {
            controller = makeController(statement);
            controller.setRepoLogFile(logFile);
            controller.executeAll();
        } catch (InterpreterError e) {
            System.out.println(e.getMessage());
        }
    }

    public void executeOne() {
        try {
            if (controller == null || !controller.oneStep()) {
                controller = makeController(statement);
                controller.setRepoLogFile(logFile);
            }
        } catch (InterpreterError e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeCompletedThreads() {
        controller.removeCompletedThreads();
    }

    @Override
    public void setLogFile(String file) {
        logFile = file;
    }
}
