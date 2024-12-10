package Lang.Model.Values;

import Lang.Model.Types.ReferenceType;
import Lang.Model.Types.Type;

public class ReferenceValue implements Value {
    private final int address;
    private final Type locationType;

    public ReferenceValue(int addr, Type type) {
        address = addr;
        locationType = type;
    }

    public ReferenceValue(Type type) {
        address = 0;
        locationType = type;
    }

    @Override
    public Type getType() {
        return new ReferenceType(locationType);
    }

    public int getAddress() {
        return address;
    }

    public Type getLocationType() {
        return locationType;
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType.toString() + ")";
    }
}
