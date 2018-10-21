package se.uu.ub.cora.data.light;

import java.util.Map;

abstract class LightData {
    int name;
    int repeatId;
    Map<Integer, Integer> attributes;

    LightData(int name, int repeatId, Map<Integer, Integer> attributes) {
        this.name = name;
        this.repeatId = repeatId;
        this.attributes = attributes;
    }
}
