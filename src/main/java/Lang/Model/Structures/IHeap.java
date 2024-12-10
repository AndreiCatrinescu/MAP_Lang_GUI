package Lang.Model.Structures;

import Lang.Model.Values.Value;

import java.util.Map;

public interface IHeap {
    Integer insert(Value value);
    Value get(Integer key);
    void remove(Integer key);
    boolean isAllocated(Integer key);
    void put(Integer key, Value value);
    Map<Integer, Value> getContent();
    void setContent(Map<Integer, Value> content);
}
