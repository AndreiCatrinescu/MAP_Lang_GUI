package Lang.View;

import Lang.Exceptions.InterpreterError;
import Lang.Model.Statements.*;

public class Interpreter {

    public static void main(String[] args) throws InterpreterError {
        Statement ex1 = Examples.example1();
        Statement ex2 = Examples.example2();
        Statement ex3 = Examples.example3();
        Statement ex4 = Examples.example4();
        Statement ex5 = Examples.example5();
        Statement ex6 = Examples.example6();
        Statement ex7 = Examples.example7();
        Statement ex8 = Examples.example8();
        Statement err1 = Examples.exampleError1();
        Statement err2 = Examples.exampleError2();
        Statement err3 = Examples.exampleError3();
        Statement err4 = Examples.exampleError4();

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("exit", "exits the program"));
        menu.addCommand(new RunExample("1", ex1.toString(), ex1));
        menu.addCommand(new RunExample("2", ex2.toString(), ex2));
        menu.addCommand(new RunExample("3", ex3.toString(), ex3));
        menu.addCommand(new RunExample("4", ex4.toString(), ex4));
        menu.addCommand(new RunExample("5", ex5.toString(), ex5));
        menu.addCommand(new RunExample("6", ex6.toString(), ex6));
        menu.addCommand(new RunExample("7", ex7.toString(), ex7));
        menu.addCommand(new RunExample("8", ex8.toString(), ex8));
        menu.addCommand(new RunExample("e1", err1.toString(), err1));
        menu.addCommand(new RunExample("e2", err2.toString(), err2));
        menu.addCommand(new RunExample("e3", err3.toString(), err3));
        menu.addCommand(new RunExample("e4", err4.toString(), err4));

        menu.show();
    }
}
