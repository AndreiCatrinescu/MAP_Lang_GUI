package Lang.Model.Values;

import Lang.Model.Types.StringType;
import Lang.Model.Types.Type;

public class StringValue implements Value {
    private final String val;

    public StringValue() {
        val = "";
    }

    public StringValue(String v) {
        val = v;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    public String getVal() {
        return val;
    }

    @Override
    public String toString() {
        return '"' + val + '"';
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StringValue))
            return false;
        return val.equals(((StringValue) other).getVal());
    }
}
