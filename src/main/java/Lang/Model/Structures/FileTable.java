package Lang.Model.Structures;
import java.util.HashMap;
import java.util.Map;

public class FileTable<K, V> implements MyTable<K, V>{
    private final Map<K, V> files;

    public FileTable() {
        files = new HashMap<>();
    }

    @Override
    public V get(K key) {
        return files.get(key);
    }

    @Override
    public void put(K key, V val) {
        files.put(key, val);
    }

    @Override
    public boolean isDefined(K key) {
        return files.containsKey(key);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (var v: files.entrySet())
            output.append(v.getKey().toString()).append("\n");
        return output.toString();
    }

    @Override
    public void remove(K key) {
        files.remove(key);
    }

    @Override
    public Map<K, V> getContent() {
        return files;
    }

    @Override
    public MyTable<K, V> copy() {
        return null;
    }
}
