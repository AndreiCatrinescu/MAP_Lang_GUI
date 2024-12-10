package Lang.Model.Types;

import Lang.Model.Values.StringValue;
import Lang.Model.Values.Value;

public class StringType implements Type{
    @Override
    public Value getDefaultValue() {
        return new StringValue();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StringType;
    }

    @Override
    public String toString() {
        return "String";
    }
}
