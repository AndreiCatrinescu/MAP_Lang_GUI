package Lang.Model.Structures;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable<K, V> implements MyTable<K, V> {
    private final Map<K, V> symbols;

    public SymbolTable() {
        symbols = new HashMap<>();
    }

    @Override
    public V get(K key) {
        return symbols.get(key);
    }

    @Override
    public void put(K key, V val) {
        symbols.put(key, val);
    }

    @Override
    public boolean isDefined(K key) {
        return symbols.containsKey(key);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (var v: symbols.entrySet())
            output.append(v.getKey().toString()).append("=").append(v.getValue().toString()).append("\n");
        return output.toString();
    }

    @Override
    public void remove(K key) {
        symbols.remove(key);
    }

    @Override
    public Map<K, V> getContent() {
        return symbols;
    }

    @Override
    public MyTable<K, V> copy() {
        MyTable<K, V> newSymTable = new SymbolTable<>();
        for (var v: symbols.entrySet())
            newSymTable.put(v.getKey(), v.getValue());

        return newSymTable;
    }
}
