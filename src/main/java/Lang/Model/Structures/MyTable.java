package Lang.Model.Structures;

import java.util.Map;

public interface MyTable<K, V> {
    V get(K key);
    void put(K key, V val);
    boolean isDefined(K key);
    void remove(K key);
    Map<K, V> getContent();
    MyTable<K, V> copy();
}
