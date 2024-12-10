package Lang.Model.Types;

import Lang.Model.Values.BoolValue;
import Lang.Model.Values.Value;

public class BoolType implements Type {
    @Override
    public boolean equals(Object another) {
        return another instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public Value getDefaultValue() {
        return new BoolValue();
    }
}
