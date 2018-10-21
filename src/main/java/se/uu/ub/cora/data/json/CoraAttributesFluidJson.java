package se.uu.ub.cora.data.json;

public class CoraAttributesFluidJson {
    private FluidJson attributes;

    private CoraAttributesFluidJson() {
        attributes = FluidJson.json();
    }

    public static CoraAttributesFluidJson attributes() {
        return new CoraAttributesFluidJson();
    }

    public CoraAttributesFluidJson attribute(String name, String value) throws FluidJsonException {
        attributes.property(name, value);
        return this;
    }

    public FluidJson build() {
        return attributes;
    }
}