package Lang.Model.Structures;

import java.util.Stack;

public class ExecutionStack<T> implements MyStack<T> {
    private final Stack<T> stack;

    public ExecutionStack() {
        stack = new Stack<>();
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T val) {
        stack.push(val);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public Stack<T> getContent() {
        return stack;
    }

    @Override
    public String toString() {
        if (stack.isEmpty())
            return "";

        else if (stack.size() == 1) {
            return stack.getFirst().toString() + '\n';
        }
        else
            return stack.getLast().toString() + '\n' + stack.getFirst().toString() + '\n';
    }
}
