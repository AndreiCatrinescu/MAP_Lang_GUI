package Lang.Model.Structures;

import java.util.Stack;

public interface MyStack<T> {
    T pop();
    void push(T v);
    boolean isEmpty();
    Stack<T> getContent();
    void clear();
}
