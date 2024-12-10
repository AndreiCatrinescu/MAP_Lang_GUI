package Lang.Exceptions;

public class FileNotOpened extends InterpreterError{
    public FileNotOpened(String varName) {
        super("File " + varName + " Not Opened Before Use");
    }

}
