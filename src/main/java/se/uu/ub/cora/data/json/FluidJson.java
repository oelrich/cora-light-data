package se.uu.ub.cora.data.json;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FluidJson {
    private JSONObject fluidBase;

    private FluidJson() {
        fluidBase = new JSONObject();
    }
    public static FluidJson json() {
        return new FluidJson();
    }

    public JSONObject build() {
        return fluidBase;
    }

    public FluidJson property(String name, Object value) throws FluidJsonException {
        possiblyAddObjectAsProperty(name, value);
        return this;
    }

    private void possiblyAddObjectAsProperty(String name, Object value) throws FluidJsonException {
        if(hasAValidFluidJsonType(value)) {
            prepareAndInsertObjectAsProperty(name, value);
        } else {
            throw new FluidJsonException("type not implemented for property value");
        }
    }

    private boolean hasAValidFluidJsonType(Object value) {
        return value instanceof String || value instanceof FluidJson;
    }

    private void prepareAndInsertObjectAsProperty(String name, Object value) {
        var realisedValue = prepareValueForInsertionAsProperty(value);
        fluidBase.put(name, realisedValue);
    }

    private Object prepareValueForInsertionAsProperty(Object value) {
        if(value instanceof FluidJson) {
            return ((FluidJson) value).build();
        }
        return value;
    }

    public FluidJson array(String name, Object... values) throws FluidJsonException {
        possiblyAddArrayAsProperty(name, values);
        return this;
    }

    private void possiblyAddArrayAsProperty(String name, Object[] values) throws FluidJsonException {
        if(allValuesHaveAValidFluidJsonType(values)) {
            prepareAndInsertArrayAsProperty(name, values);
        } else {
            throw new FluidJsonException("type not implemented for property value");
        }
    }

    private boolean allValuesHaveAValidFluidJsonType(Object[] values) {
        return Arrays.stream(values).allMatch(this::hasAValidFluidJsonType);
    }

    private void prepareAndInsertArrayAsProperty(String name, Object[] values) {
        var preparedValues = Arrays.stream(values).map(this::prepareValueForInsertionAsProperty).collect(Collectors.toList());
        fluidBase.put(name, preparedValues);
    }
}