package Lang.Model.Structures;

import Lang.Exceptions.ExecutionStackEmpty;
import Lang.Exceptions.InterpreterError;
import Lang.Model.Statements.Statement;
import Lang.Model.Values.StringValue;
import Lang.Model.Values.Value;

import java.io.BufferedReader;

public class ProgramState {
    private final MyStack<Statement> exeStack;
    private final MyList<Value> out;
    private final MyTable<String, Value> symTable;
    private final MyTable<StringValue, BufferedReader> fileTable;

    public int getId() {
        return id;
    }

    private final int id;
    private static int lastId = 0;

    public ProgramState executeOne() throws InterpreterError {
        if (exeStack.isEmpty())
            throw new ExecutionStackEmpty();

        Statement currentStatement = exeStack.pop();
        try {
            return currentStatement.execute(this);
        } catch (InterpreterError e) {
            exeStack.clear();
            throw e;
        }
    }

    public boolean isDone() {
        return exeStack.isEmpty();
    }

    public IHeap getHeap() {
        return heap;
    }
    private final IHeap heap;

    public ProgramState(Statement statement,
                        MyStack<Statement> exeStack,
                        MyList<Value> out,
                        MyTable<String, Value> symTable,
                        MyTable<StringValue, BufferedReader> fileTable,
                        IHeap heap) {
        this.exeStack = exeStack;
        this.fileTable = fileTable;
        this.out = out;
        this.symTable = symTable;
        this.heap = heap;
        id = lastId;
        lastId++;
        exeStack.push(statement);
    }

    public MyStack<Statement> getExeStack() {
        return exeStack;
    }

    public MyList<Value> getOut() {
        return out;
    }

    public MyTable<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyTable<String, Value> getSymTable() {
        return symTable;
    }

    @Override
    public String toString() {
        return "ID " + id
                + "\nExecution Stack:\n" + exeStack.toString()
                + "Symbol table:\n" + symTable.toString()
                + "Output:\n" + out.toString()
                + "Files:\n" + fileTable.toString()
                + "Heap:\n" + heap.toString()
                + '\n';
    }

    private String overcomplicatedToString(MyStack<Statement> stack) {
        TreeBuilder<Statement> treeBuilder = new BinaryTreeBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        for (var statement: stack.getContent()) {
            var current = treeBuilder.buildTree(statement);
            StringBuilder subtreeString = current.traversal();
            stringBuilder.append(subtreeString);
        }
        return stringBuilder.toString();
    }
}
