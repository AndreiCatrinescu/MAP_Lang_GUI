package Lang.Model.Types;

import Lang.Model.Values.IntValue;
import Lang.Model.Values.Value;

public class IntType implements Type {
    @Override
    public boolean equals(Object another) {
        return another instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public Value getDefaultValue() {
        return new IntValue();
    }
}
