package Lang.Repo;

import Lang.Exceptions.IOError;
import Lang.Exceptions.InterpreterError;
import Lang.Model.Structures.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ProgramRepo implements Repository {
    private List<ProgramState> states;
    private String logFilePath;

    public ProgramRepo(ProgramState cs) {
        logFilePath = "log.txt";
        states = new ArrayList<>();
        states.add(cs);
    }

    public ProgramRepo(ProgramState cs, String logFile) {
        logFilePath = logFile;
        states = new ArrayList<>();
        states.add(cs);
    }


    @Override
    public void logProgramState(ProgramState curentState) throws InterpreterError {
        try (PrintWriter logFileWriter = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            logFileWriter.print(curentState.toString());
            logFileWriter.flush();
        } catch (IOException e) {
            throw new IOError("Error When Logging");
        }
    }

    @Override
    public void setLogFile(String newFile) {
        logFilePath = newFile;
    }

    @Override
    public List<ProgramState> getStates() {
        return states;
    }

    @Override
    public void setStates(List<ProgramState> sts) {
        states = sts;
    }
}
