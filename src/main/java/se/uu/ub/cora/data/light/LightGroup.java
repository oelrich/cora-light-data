package se.uu.ub.cora.data.light;

import java.util.Map;

public class LightGroup extends LightData {
    public LightData[] children;

    LightGroup(int name, int repeatId, Map<Integer, Integer> attributes, LightData[] children) {
        super(name, repeatId, attributes);
        this.children = children;
    }

    LightGroup(int name, int repeatId, Map<Integer, Integer> attributes) {
        super(name, repeatId, attributes);
        this.children = null;
    }

    LightGroup(int name, int repeatId) {
        super(name, repeatId, Map.of());
        this.children = null;
    }

    LightGroup(int name, int repeatId, LightData[] children) {
        super(name, repeatId, Map.of());
        this.children = children;
    }

    LightGroup(int name, Map<Integer, Integer> attributes, LightData[] children) {
        super(name, 0, attributes);
        this.children = children;
    }

    LightGroup(int name, Map<Integer, Integer> attributes) {
        super(name, 0, attributes);
        this.children = null;
    }

    LightGroup(int name, LightData[] children) {
        super(name, 0, Map.of());
        this.children = children;
    }

    LightGroup(int name) {
        super(name, 0, Map.of());
        this.children = null;
    }
}
