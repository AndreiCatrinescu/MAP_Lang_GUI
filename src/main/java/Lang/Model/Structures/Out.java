package Lang.Model.Structures;

import java.util.ArrayList;
import java.util.List;

public class Out<T> implements MyList<T> {
    private final List<T> elements;

    public Out() {
        elements = new ArrayList<>();
    }

    @Override
    public List<T> getAll() {
        return elements;
    }

    @Override
    public synchronized void add(T val) {
        elements.add(val);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (T elem : elements)
            output.append(elem.toString()).append("\n");
        return output.toString();
    }
}
