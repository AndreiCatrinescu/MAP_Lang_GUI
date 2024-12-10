package Lang.Model.Structures;

import Lang.Model.Values.Value;

import java.util.HashMap;
import java.util.Map;

public class Heap implements IHeap {
    private Map<Integer, Value> locations;

    private Integer freeLocation;

    public Heap() {
        freeLocation = 1;
        locations = new HashMap<>();
    }

    @Override
    public Integer insert(Value value) {
        locations.put(freeLocation, value);
        return freeLocation++;
    }

    @Override
    public Value get(Integer key) {
        return locations.get(key);
    }

    @Override
    public void remove(Integer key) {
        locations.remove(key);
    }

    @Override
    public boolean isAllocated(Integer key) {
        return key != 0 && locations.containsKey(key);
    }

    @Override
    public void put(Integer key, Value value) {
        locations.put(key, value);
    }

    @Override
    public Map<Integer, Value> getContent() {
        return locations;
    }

    @Override
    public void setContent(Map<Integer, Value> content) {
        locations = content;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (var v: locations.entrySet())
            output.append(v.getKey().toString()).append("->").append(v.getValue().toString()).append("\n");
        return output.toString();
    }
}
