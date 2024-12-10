package Lang.Exceptions;

public class FileAlreadyOpened extends InterpreterError{
    public FileAlreadyOpened(String filePath) {
        super("File " + filePath + " Already Opened");
    }

}
