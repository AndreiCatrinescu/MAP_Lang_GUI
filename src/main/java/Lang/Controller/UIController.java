package Lang.Controller;

import Lang.Model.Structures.ProgramState;
import Lang.Model.Values.Value;
import Lang.View.RunExample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Map;
import java.util.NoSuchElementException;

public class UIController {
    private RunExample currentCommand;

    public ObservableList<String> getOutContent() {
        return outContent;
    }

    public ObservableList<Integer> getStatesListContent() {
        return statesListContent;
    }

    public ObservableList<String> getFileTableContent() {
        return fileTableContent;
    }

    public ObservableList<Map.Entry<Integer, Value>> getHeapContent() {
        return heapContent;
    }

    public ObservableList<Map.Entry<String, Value>> getSymTableContent() {
        return symTableContent;
    }

    public ObservableList<String> getExeStackContent() {
        return exeStackContent;
    }

    private ObservableList<String> outContent;
    private ObservableList<Integer> statesListContent;
    private ObservableList<String> fileTableContent;
    private ObservableList<Map.Entry<Integer, Value>> heapContent;
    private ObservableList<Map.Entry<String, Value>> symTableContent;
    private ObservableList<String> exeStackContent;

    private int currentId;

    public void setCurrentId(int id) {
        currentId = id;
    }

    public void executeCommand() {
        currentCommand.executeOne();
        outContent = updateOut();
        fileTableContent = updateFileTable();
        heapContent = updateHeap();
        currentCommand.removeCompletedThreads();
        statesListContent = updateStatesList();
        symTableContent = updateSymTable();
        exeStackContent = updateExeStack();
    }

    private ObservableList<String> updateOut() {
        try {
            var out = currentCommand.getProgramStates().getFirst().getOut().getAll();
            var updateContent = FXCollections.<String>observableArrayList();
            for (var o : out) {
                updateContent.add(o.toString());
            }
            return updateContent;
        } catch (Exception e) {
            return FXCollections.observableArrayList();
        }
    }

    private ObservableList<Integer> updateStatesList() {
        var states = currentCommand.getProgramStates();
        var updateContent = FXCollections.<Integer>observableArrayList();
        for (var s : states) {
            updateContent.add(s.getId());
        }
        return updateContent;
    }

    private ObservableList<String> updateFileTable() {
        try {
            var files = currentCommand.getProgramStates().getFirst().getFileTable().getContent();
            var updateContent = FXCollections.<String>observableArrayList();
            for (var f : files.keySet()) {
                updateContent.add(f.toString());
            }
            return updateContent;
        } catch (Exception e) {
            return FXCollections.observableArrayList();
        }
    }

    private ObservableList<Map.Entry<Integer, Value>> updateHeap() {
        try {
            var heap = currentCommand.getProgramStates().getFirst().getHeap().getContent();
            var updateContent = FXCollections.<Map.Entry<Integer, Value>>observableArrayList();
            updateContent.addAll(heap.entrySet());
            return updateContent;
        } catch (Exception e) {
            return FXCollections.observableArrayList();
        }
    }

    private ObservableList<Map.Entry<String, Value>> updateSymTable() {
        ProgramState state;
        try {
            state = currentCommand.getProgramStates()
                    .stream()
                    .filter(s -> s.getId() == currentId).toList().getFirst();
        } catch (NoSuchElementException e) {
            try {
                currentId = currentCommand.getProgramStates().getFirst().getId();
                state = currentCommand.getProgramStates().get(currentId);
            } catch (Exception e2) {
                return FXCollections.observableArrayList();
            }
        }

        var sym = state.getSymTable().getContent();

        var updateContent = FXCollections.<Map.Entry<String, Value>>observableArrayList();
        updateContent.addAll(sym.entrySet());

        return updateContent;
    }

    private ObservableList<String> updateExeStack() {
        ProgramState state;
        try {
            state = currentCommand.getProgramStates()
                    .stream()
                    .filter(s -> s.getId() == currentId).toList().getFirst();
        } catch (NoSuchElementException e) {
            try {
                currentId = currentCommand.getProgramStates().getFirst().getId();
                state = currentCommand.getProgramStates().get(currentId);
            } catch (Exception e2) {
                return FXCollections.observableArrayList();
            }
        }

        var exe = state.getExeStack().getContent();

        var updateContent = FXCollections.<String>observableArrayList();
        for (var e : exe.reversed()) {
            updateContent.add(e.toString());
        }

        return updateContent;
    }

    public void setCurrentCommand(RunExample command) {
        currentCommand = command;
        outContent = updateOut();
        fileTableContent = updateFileTable();
        heapContent = updateHeap();
        statesListContent = updateStatesList();
        symTableContent = updateSymTable();
        exeStackContent = updateExeStack();
    }
}
