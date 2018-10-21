package se.uu.ub.cora.data.light;

import java.util.Map;

public class LightAtom extends LightData {
    public int value;

    public LightAtom(int name, int repeatId, int value) {
        super(name, repeatId, Map.of());
        this.value = value;
    }

    public LightAtom(int name, int value) {
        super(name, 0, Map.of());
        this.value = value;
    }
}
