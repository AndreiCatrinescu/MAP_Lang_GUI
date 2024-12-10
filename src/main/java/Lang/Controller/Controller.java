package Lang.Controller;

import Lang.Exceptions.InterpreterError;
import Lang.Model.Structures.IHeap;
import Lang.Model.Structures.ProgramState;
import Lang.Model.Values.ReferenceValue;
import Lang.Model.Values.Value;
import Lang.Repo.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private final Repository repo;
    private ExecutorService executor;

    public List<ProgramState> getStates() {
        return repo.getStates();
    }

    private static Map<Integer, Value> garbageCollector(List<Integer> addresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> addresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static List<Integer> getAddresses(Collection<Value> symTableValues, Collection<Value> heapValues) {
        var symTable = symTableValues.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> {
                    ReferenceValue v1 = (ReferenceValue) v;
                    return v1.getAddress();
                });

        var heap = heapValues.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> {
                    ReferenceValue v1 = (ReferenceValue) v;
                    return v1.getAddress();
                });

        return Stream.concat(symTable, heap).collect(Collectors.toList());
    }

    public Controller(Repository r) {
        repo = r;
    }

    public void removeCompletedThreads() {
        repo.setStates(removeCompletedThreads(repo.getStates()));
    }

    public boolean oneStep() {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> stateList = repo.getStates();
        if (stateList.isEmpty())
            return false;
        List<Value> values = stateList.stream()
                .map(programState -> programState.getSymTable().getContent().values())
                .flatMap(Collection::stream)
                .toList();

        IHeap heap = stateList.getFirst().getHeap();

        List<Integer> addresses = getAddresses(values, heap.getContent().values());

        stateList.getFirst().getHeap().setContent(garbageCollector(addresses, heap.getContent()));

        executeOneForAll(stateList);
//        stateList = removeCompletedThreads(repo.getStates());
        executor.shutdownNow();

        repo.setStates(stateList);

        return true;
    }

    private void executeOneForAll(List<ProgramState> states) {
//        states.forEach(repo::logProgramState);
        List<Callable<ProgramState>> callList = states.stream().map(programState -> (Callable<ProgramState>) (programState::executeOne))
                .toList();

        try {
            List<ProgramState> spawnedThreads = executor.invokeAll(callList).stream()
                    .map(future ->
                    {
                        try {
                            return future.get();
                        } catch (InterpreterError | InterruptedException | ExecutionException e) {
                            System.out.println(e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
            states.addAll(spawnedThreads);
            states.forEach(repo::logProgramState);
            repo.setStates(states);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    List<ProgramState> removeCompletedThreads(List<ProgramState> states) {
        return states.stream().filter(state -> !state.isDone()).collect(Collectors.toList());
    }

    public void executeAll() {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> stateList = removeCompletedThreads(repo.getStates());
        while (!stateList.isEmpty()) {
            List<Value> values = stateList.stream()
                    .map(programState -> programState.getSymTable().getContent().values())
                    .flatMap(Collection::stream)
                    .toList();

            IHeap heap = stateList.getFirst().getHeap();

            List<Integer> addresses = getAddresses(values, heap.getContent().values());

            stateList.getFirst().getHeap().setContent(garbageCollector(addresses, heap.getContent()));

            executeOneForAll(stateList);
            stateList = removeCompletedThreads(repo.getStates());
        }
        executor.shutdownNow();

        repo.setStates(stateList);
    }

    public void setRepoLogFile(String newFile) {
        repo.setLogFile(newFile);
    }
}
