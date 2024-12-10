package Lang.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private final Map<String, Command> commands;

    public TextMenu() {
        commands = new HashMap<>();
    }

    public void addCommand(Command c) {
        commands.put(c.getKey(), c);
    }

    private void printMenu() {
        for (Command com : commands.values()) {
            String line = String.format("=====================\n%4s:\n%s", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }

    public void show() {
        Scanner userInput = new Scanner(System.in);
        while (true) {
            printMenu();

            System.out.print("Choose a program to run: ");
            String option = userInput.nextLine();

            Command command = commands.get(option);
            if (command == null) {
                System.out.println("Invalid Option");
                continue;
            }

            if(command instanceof ExitCommand) {
                command.execute();
            }

            System.out.print("File to log execution: ");
            String logFile = userInput.nextLine();

            if(!logFile.isEmpty()) {
                command.setLogFile(logFile);
            }

            command.execute();
        }
    }
}
