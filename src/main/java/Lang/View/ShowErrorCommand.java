package Lang.View;

public class ShowErrorCommand extends Command{
    private final String error;

    public ShowErrorCommand(String key, String desc, String e) {
        super(key, desc);
        error = e;
    }

    @Override
    public void execute() {
        System.out.println("Compilation error: \033[31m");
        System.out.println(error);
        System.out.println("\033[0m");
    }

    @Override
    public void setLogFile(String file) {
        /*does nothing*/
    }
}
