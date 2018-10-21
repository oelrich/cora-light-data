package se.uu.ub.cora.data.json;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class FluidJsonTest {

    @Test
    public void initTest() {
        assertNotNull(FluidJson.json());
    }

    @Test
    public void testBuildToJson() {
        var fluidJson = FluidJson.json();
        assertNotNull(fluidJson.build());
    }

    @Test
    public void testEmptyFluidJsonBuildsToEmptyObject() {
        var fluidJsonAsText =
                FluidJson.json().build().toString();

        assertEquals(fluidJsonAsText, "{}");
    }

    @Test
    public void testFluidJsonWithTextProperty() throws FluidJsonException {
        var fluidJsonAsText = FluidJson.json()
                .property("someProperty", "someValue")
                .build().toString();

        assertEquals(fluidJsonAsText,
                "{\"someProperty\":\"someValue\"}");
    }

    @Test
    public void testFluidJsonWithSecondTextProperty() throws FluidJsonException {
        var fluidJsonAsText = FluidJson.json()
                .property("someProperty", "someValue")
                .property("someOtherProperty", "someOtherValue")
                .build().toString();

        assertEquals(fluidJsonAsText,
                "{\"someProperty\":\"someValue\",\"someOtherProperty\":\"someOtherValue\"}");
    }

    @Test(expectedExceptions = FluidJsonException.class, expectedExceptionsMessageRegExp = "type not implemented for property value")
    public void testFluidJsonWithNonStringProperty() throws FluidJsonException {
        FluidJson.json()
                .property("someProperty", 666);
    }

    @Test
    public void testFluidJsonWithEmptyChildObject() throws FluidJsonException {
        var fluidJsonAsText = FluidJson.json()
                .property("someProperty",
                        FluidJson.json())
                .build().toString();
        assertEquals(fluidJsonAsText,
                "{\"someProperty\":{}}");
    }

    @Test
    public void testFluidJsonWithChildObject() throws FluidJsonException {
        var fluidJsonAsText = FluidJson.json()
                .property("someProperty",
                        FluidJson.json()
                                .property("childName", "childValue"))
                .build().toString();
        assertEquals(fluidJsonAsText,
                "{\"someProperty\":{\"childName\":\"childValue\"}}");
    }

    @Test
    public void testFluidJsonWithImplicitEmptyArrayProperty() throws FluidJsonException {
        var fluidJsonAsText = FluidJson.json()
                .array("someProperty")
                .build().toString();

        assertEquals(fluidJsonAsText,
                "{\"someProperty\":[]}");
    }

    @Test
    public void testFluidJsonWithTextArray() throws FluidJsonException {
        var fluidJsonAsText = FluidJson.json()
                .array("someProperty", "someValue")
                .build().toString();

        assertEquals(fluidJsonAsText,
                "{\"someProperty\":[\"someValue\"]}");
    }

    @Test
    public void testFluidJsonWithTextArrayFromVararg() throws FluidJsonException {
        var fluidJsonAsText = FluidJson.json()
                .array("someProperty", "firstValue", "secondValue")
                .build().toString();

        assertEquals(fluidJsonAsText,
                "{\"someProperty\":[\"firstValue\",\"secondValue\"]}");
    }

    @Test(expectedExceptions = FluidJsonException.class, expectedExceptionsMessageRegExp = "type not implemented for property value")
    public void testFluidJsonWithUnimplementedArrayTypes() throws FluidJsonException {
        FluidJson.json()
                .array("someProperty", 666)
                .build();
    }

    @Test
    public void testFluidJsonWithMixedArrayTypes() throws FluidJsonException {
        var fluidJsonAsText = FluidJson.json()
                .array("someProperty",
                        "someValue", FluidJson.json())
                .build().toString();
        assertEquals(fluidJsonAsText,
                "{\"someProperty\":[\"someValue\",{}]}");
    }

    @Test
    public void testFluidJsonWithMixedNonEmptyArrayTypes() throws FluidJsonException {
        var fluidJsonAsText = FluidJson.json()
                .array("someProperty",
                        "someValue",
                        FluidJson.json()
                                .property("childName", "childValue"))
                .build().toString();
        assertEquals(fluidJsonAsText,
                "{\"someProperty\":[\"someValue\",{\"childName\":\"childValue\"}]}");
    }

    @Test
    public void testFluidJsonWithEmptyArrayProperty() throws FluidJsonException {
        var fluidJsonAsText = FluidJson.json()
                .array("someProperty", new String[]{})
                .build().toString();

        assertEquals(fluidJsonAsText,
                "{\"someProperty\":[]}");
    }

    @Test
    public void testFluidJsonWithTextArrayProperty() throws FluidJsonException {
        var fluidJsonAsText = FluidJson.json()
                .array("someProperty", new String[]{"someValue"})
                .build().toString();

        assertEquals(fluidJsonAsText,
                "{\"someProperty\":[\"someValue\"]}");
    }




}
