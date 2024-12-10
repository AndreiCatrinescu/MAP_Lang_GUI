package Lang.Model.Types;

import Lang.Model.Values.ReferenceValue;
import Lang.Model.Values.Value;

public class ReferenceType implements Type{
    private final Type innerType;

    public ReferenceType(Type inner) {
        this.innerType = inner;
    }

    public Type getInnerType() {
        return innerType;
    }

    @Override
    public Value getDefaultValue() {
        return new ReferenceValue(innerType);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReferenceType) {
            return innerType.equals(((ReferenceType)obj).getInnerType());
        }
        else return false;
    }

    @Override
    public String toString() {
        return innerType.toString() + "*";
    }
}
