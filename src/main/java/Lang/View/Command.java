package Lang.View;

public abstract class Command {
    private final String key;
    private final String description;
    protected String logFile;

    public Command(String key, String desc) {
        this.key = key;
        description = desc;
        logFile = "log.txt";
    }
    public abstract void execute();
    public String getKey() {
        return key;
    }
    public String getDescription() {
        return description;
    }
    public abstract void setLogFile(String file);
}
