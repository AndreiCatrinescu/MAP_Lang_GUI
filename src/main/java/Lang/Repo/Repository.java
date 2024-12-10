package Lang.Repo;

import Lang.Exceptions.InterpreterError;
import Lang.Model.Structures.ProgramState;

import java.util.List;

public interface Repository {
    void logProgramState(ProgramState currentState) throws InterpreterError;
    void setLogFile(String newFile);
    List<ProgramState> getStates();
    void setStates(List<ProgramState> sts);
}
