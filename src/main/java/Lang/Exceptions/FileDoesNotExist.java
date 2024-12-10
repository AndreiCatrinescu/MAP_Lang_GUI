package Lang.Exceptions;

public class FileDoesNotExist extends InterpreterError {
    public FileDoesNotExist(String filePath) {
        super("File at " + filePath + " Does Not Exist");
    }
}
