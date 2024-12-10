package Lang.Model.Values;

import Lang.Model.Types.IntType;
import Lang.Model.Types.Type;

public class IntValue implements Value {
    private final int val;

    public IntValue() {
        val = 0;
    }

    public IntValue(int v) {
        val = v;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    public int getVal() {
        return val;
    }

    @Override
    public String toString() {
        return "" + val;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof IntValue))
            return false;
        return val == ((IntValue) other).getVal();
    }
}
