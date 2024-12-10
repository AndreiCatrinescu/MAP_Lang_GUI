package Lang.Model.Values;

import Lang.Model.Types.BoolType;
import Lang.Model.Types.Type;

public class BoolValue implements Value {
    private final boolean val;

    public BoolValue() {
        val = false;
    }

    public BoolValue(boolean v) {
        val = v;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    public boolean getVal() {
        return val;
    }

    @Override
    public String toString() {
        return "" + val;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof BoolValue))
            return false;
        return val == ((BoolValue) other).getVal();
    }
}
