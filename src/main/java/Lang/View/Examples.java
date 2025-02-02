package Lang.View;

import Lang.Exceptions.AllocationError;
import Lang.Model.Expressions.*;
import Lang.Model.Statements.*;
import Lang.Model.Types.BoolType;
import Lang.Model.Types.IntType;
import Lang.Model.Types.ReferenceType;
import Lang.Model.Types.StringType;
import Lang.Model.Values.BoolValue;
import Lang.Model.Values.IntValue;
import Lang.Model.Values.StringValue;

import java.util.ArrayList;

public class Examples {
    static Statement combine(Statement first, Statement last) {
        return new CompStatement(first, last);
    }

    static Statement exampleError4() {
        Statement declareA = new DeclarationStatement("a", new IntType());
        Statement thread = new ThreadStatement(new DeclarationStatement("a", new BoolType()));
        return combine(declareA, thread);
    }

    static Statement exampleError3() {
        Statement declareD = new DeclarationStatement("d", new BoolType());
        Statement declareA1 = new DeclarationStatement("a1", new IntType());
        Statement assignA1 = new AssignStatement("a1", new LiteralExp(new IntValue(20)));
        Statement declareA2 = new DeclarationStatement("a2", new IntType());
        Statement assignA2 = new AssignStatement("a2", new LiteralExp(new IntValue(20)));
        Statement trueBranch = combine(declareA1, assignA1);
        Statement falseBranch = combine(declareA2, assignA2);
        Statement brokenIf = new IfStatement(new VariableExp("d"), trueBranch, falseBranch);
        Statement printA1 = new PrintStatement(new VariableExp("a1"));
        Statement ex10 = combine(brokenIf, printA1);
        ex10 = combine(declareD, ex10);
        return ex10;
    }

    static Statement exampleError2() {
        Statement declareV = new DeclarationStatement("v", new IntType());

        Statement assignV = new AssignStatement("v", new LiteralExp(new IntValue(4)));

        Statement whileBody = combine(new PrintStatement(new VariableExp("v")),
                new AssignStatement("v", new ArithmeticExp(
                        new VariableExp("v"),
                        new LiteralExp(new IntValue(1)),
                        IntOperation.Sub)));
        whileBody = combine(new DeclarationStatement("lol", new IntType()), whileBody);

        Statement whileV = new WhileStatement(
                new CompareIntExp(
                        new VariableExp("v"),
                        new LiteralExp(new IntValue(0)),
                        CompOperator.Greater),
                whileBody
                );
        Statement assignA = new AssignStatement("lol", new LiteralExp(new IntValue(10)));
        Statement ex9;
        ex9 = combine(whileV, assignA);
        ex9 = combine(assignV, ex9);
        ex9 = combine(declareV, ex9);
        return ex9;
    }

    static Statement example9 () {
        /*
        int *a;
        int v;
        new (a, 10);
        fork{
            v = 20;
            fork {
                *a = 40;
                print(*a);
            }
            print(v);
         }
         v = 30;
         print(v);
         print(*a);
         */
        Statement declA = new DeclarationStatement("a", new ReferenceType(new IntType()));
        Statement declV = new DeclarationStatement("v", new IntType());
        Statement allocA = new NewStatement("a", new LiteralExp(new IntValue(10)));

        // fork2
        Statement fork2Body = new CompStatement(
                new DerefAssignStatement(
                        "a",
                        new LiteralExp(
                                new IntValue(40))),
                new PrintStatement(new DerefExp(new VariableExp("a"))));

        Statement fork2 = new ThreadStatement(fork2Body);

        // fork1
        Statement fork1 = new ThreadStatement(new CompStatement(new AssignStatement("v", new LiteralExp(new IntValue(20))),
                new CompStatement(fork2, new PrintStatement(new VariableExp("v")))));

        Statement assignV = new AssignStatement("v", new LiteralExp(new IntValue(30)));

        Statement printV = new PrintStatement(new VariableExp("v"));

        Statement printA = new PrintStatement(new DerefExp(new VariableExp("a")));

        Statement ex9;
        ex9 = combine(printV, printA);
        ex9 = combine(assignV, ex9);
        ex9 = combine(fork1, ex9);
        ex9 = combine(allocA, ex9);
        ex9 = combine(declV, ex9);
        ex9 = combine(declA, ex9);

        return ex9;
    }

    static Statement example8() {
        // main
        Statement declareV = new DeclarationStatement("v", new IntType());

        Statement declareA = new DeclarationStatement("a", new ReferenceType(new IntType()));

        Statement assignV = new AssignStatement("v", new LiteralExp(new IntValue(10)));

        Statement allocA = new NewStatement("a", new LiteralExp(new IntValue(22)));

        Expression derefA = new DerefExp(new VariableExp("a"));

        Statement printV = new PrintStatement(new VariableExp("v"));

        Statement printA = new PrintStatement(derefA);

        // thread
        Statement threadAssignA = new DerefAssignStatement("a", new LiteralExp(new IntValue(30)));

        Statement threadAssignV = new AssignStatement("v", new LiteralExp(new IntValue(50)));

        Statement threadPrintV = new PrintStatement(new VariableExp("v"));

        Expression threadDerefA = new DerefExp(new VariableExp("a"));

        Statement threadPrintA = new PrintStatement(threadDerefA);

        Statement threadBody = combine(threadPrintV, threadPrintA);
        threadBody = combine(threadAssignV,threadBody);
        threadBody = combine(threadAssignA, threadBody);

        Statement spawnThread = new ThreadStatement(threadBody);

        Statement ex8;
        ex8 = combine(printV, printA);
        ex8 = combine(spawnThread, ex8);
        ex8 = combine(allocA, ex8);
        ex8 = combine(assignV, ex8);
        ex8 = combine(declareA, ex8);
        ex8 = combine(declareV, ex8);
        return ex8;
    }

    static Statement example7() {
        Statement declareV = new DeclarationStatement("v", new IntType());

        Statement assignV = new AssignStatement("v", new LiteralExp(new IntValue(4)));

        Statement whileBody = combine(new PrintStatement(new VariableExp("v")),
                new AssignStatement("v", new ArithmeticExp(
                        new VariableExp("v"),
                        new LiteralExp(new IntValue(1)),
                        IntOperation.Sub)));

        Statement whileV = new WhileStatement(
                new CompareIntExp(
                        new VariableExp("v"),
                        new LiteralExp(new IntValue(0)),
                        CompOperator.Greater),
                whileBody
                );

        Statement ex7;
        ex7 = combine(assignV, whileV);
        ex7 = combine(declareV, ex7);
        return ex7;
    }

    static Statement example6() {
        Statement declareV = new DeclarationStatement("v", new ReferenceType(new IntType()));

        Statement allocV = new NewStatement("v", new LiteralExp(new IntValue(20)));

        Statement delcareA = new DeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType())));

        Statement allocA = new NewStatement("a", new VariableExp("v"));

        Expression derefV = new DerefExp(new VariableExp("v"));

        Expression derefA = new DerefExp(new VariableExp("a"));

        Expression derefATwice = new DerefExp(derefA);

        Statement printV = new PrintStatement(derefV);

        Statement writeV = new DerefAssignStatement("v", new LiteralExp(new IntValue(30)));

        Statement assignA = new NewStatement("a", new VariableExp("v"));

        Statement pringVAgain = new PrintStatement(derefV);
        Statement pringVOnceMore = new PrintStatement(derefV);

        Statement printA = new PrintStatement(
                new ArithmeticExp(derefATwice,
                new LiteralExp(new IntValue(5)),
                IntOperation.Add));

        Statement ex6;
        ex6 = combine(pringVAgain, pringVOnceMore);
        ex6 = combine(assignA, ex6);
        ex6 = combine(writeV, ex6);
        ex6 = combine(printA, ex6);
        ex6 = combine(printV, ex6);
        ex6 = combine(allocA, ex6);
        ex6 = combine(delcareA, ex6);
        ex6 = combine(allocV, ex6);
        ex6 = combine(declareV, ex6);

        return ex6;
    }

    static Statement example5() {
        Statement declareA = new DeclarationStatement("a", new IntType());

        Statement assignA = new AssignStatement("a", new LiteralExp(new IntValue(2)));

        Statement declareB = new DeclarationStatement("b", new IntType());

        Statement declareF = new DeclarationStatement("f", new StringType());

        Statement assignB = new AssignStatement("b", new LiteralExp(new IntValue(3)));

        Statement assignFTrue = new AssignStatement("f", new LiteralExp(new StringValue("aFile.txt")));

        Statement assignFFalse = new AssignStatement("f", new LiteralExp(new StringValue("bFile.txt")));

        Expression compAB = new CompareIntExp(new VariableExp("a"), new VariableExp("b"), CompOperator.Greater);

        Statement ifStatement = new IfStatement(compAB, assignFTrue, assignFFalse);

        Statement openF = new OpenStatement(new VariableExp("f"));

        Statement closeF = new CloseStatement(new VariableExp("f"));

        Statement ex5;
        ex5 = combine(openF, closeF);
        ex5 = combine(ifStatement, ex5);
        ex5 = combine(assignB, ex5);
        ex5 = combine(declareF, ex5);
        ex5 = combine(declareB, ex5);
        ex5 = combine(assignA, ex5);
        ex5 = combine(declareA, ex5);

        return ex5;
    }

    static Statement example4() {
        Statement declareF = new DeclarationStatement("f", new StringType());

        Statement assignF = new AssignStatement("f", new LiteralExp(new StringValue("test.txt")));

        Statement openFileTest = new OpenStatement(new VariableExp("f"));

        Statement declareV = new DeclarationStatement("v", new IntType());

        Statement readOne = new ReadStatement(new VariableExp("f"), "v");

        Statement printOnce = new PrintStatement(new VariableExp("v"));

        Statement readAgain = new ReadStatement(new VariableExp("f"), "v");

        Statement printAgain = new PrintStatement(new VariableExp("v"));

        Statement closeFile = new CloseStatement(new VariableExp("f"));

        Statement ex4;

        ex4 = combine(printAgain, closeFile);
        ex4 = combine(readAgain, ex4);
        ex4 = combine(printOnce, ex4);
        ex4 = combine(readOne, ex4);
        ex4 = combine(declareV, ex4);
        ex4 = combine(openFileTest, ex4);
        ex4 = combine(assignF, ex4);
        ex4 = combine(declareF, ex4);

        return ex4;
    }

    static Statement exampleError1() {
        Statement decalreA = new DeclarationStatement("a", new IntType()); // int a;

        Statement assigntWrongType = new AssignStatement("a", new LiteralExp(new BoolValue(true)));// a = true;

        Statement exErr;
        exErr = combine(decalreA, assigntWrongType);
        return assigntWrongType;
    }

    static Statement example3() {
        Statement decalreA = new DeclarationStatement("a", new BoolType()); // bool a;

        Statement declareV = new DeclarationStatement("v", new IntType()); // int v;

        Statement assignA = new AssignStatement("a", new LiteralExp(new BoolValue(true))); // a = true;

        Statement ifAThen = new IfStatement(
                new VariableExp("a"),
                new AssignStatement("v", new LiteralExp(new IntValue(2))),
                new AssignStatement("v", new LiteralExp(new IntValue(3)))); // if a then v = 2 else v = 3;

        Statement printV = new PrintStatement(new VariableExp("v")); // print(v);

        Statement ex3;
        ex3 = combine(ifAThen, printV);
        ex3 = combine(assignA, ex3);
        ex3 = combine(declareV, ex3);
        ex3 = combine(decalreA, ex3);

        return ex3;
    }

    static Statement example2() {
        Statement declareA = new DeclarationStatement("a", new IntType()); // int a;

        Statement declareB = new DeclarationStatement("b", new IntType()); // int b;

        Expression threeTimesFive = new ArithmeticExp(
                new LiteralExp(new IntValue(3)),
                new LiteralExp(new IntValue(5)),
                IntOperation.Mul); // 3 * 5

        Expression twoPlus = new ArithmeticExp(
                new LiteralExp(new IntValue(2)),
                threeTimesFive,
                IntOperation.Add); // 2 + 3 * 5

        Statement assignA = new AssignStatement("a", twoPlus); // a = 2 + 3 * 5;

        Expression aPlusOne = new ArithmeticExp(
                new VariableExp("a"),
                new LiteralExp(new IntValue(1)),
                IntOperation.Add); // a + 1

        Statement assignB = new AssignStatement("b", aPlusOne); // b = a + 1;

        Statement printB = new PrintStatement(new VariableExp("b")); // print(b);

        Statement ex2;
        ex2 = combine(assignB, printB);
        ex2 = combine(assignA, ex2);
        ex2 = combine(declareB, ex2);
        ex2 = combine(declareA, ex2);

        return ex2;
    }

    static Statement example1() {
        Statement declareV = new DeclarationStatement("v", new IntType()); // int v;

        Statement assignV = new AssignStatement("v", new LiteralExp(new IntValue(2))); // v = 2;

        Statement printV = new PrintStatement(new VariableExp("v")); // print(v);

        Statement ex1;
        ex1 = combine(assignV, printV);
        ex1 = combine(declareV, ex1);
        return ex1;
    }

    static ArrayList<Statement> getAllExamples() {
        ArrayList<Statement> exampleList = new ArrayList<>();
        exampleList.add(example1());
        exampleList.add(example2());
        exampleList.add(example3());
        exampleList.add(example4());
        exampleList.add(example5());
        exampleList.add(example6());
        exampleList.add(example7());
        exampleList.add(example8());
        exampleList.add(example9());
        exampleList.add(exampleError1());
        exampleList.add(exampleError2());
        exampleList.add(exampleError3());
        exampleList.add(exampleError4());
        return exampleList;
    }
}
