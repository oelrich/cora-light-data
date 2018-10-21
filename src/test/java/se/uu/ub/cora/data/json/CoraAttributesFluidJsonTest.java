package se.uu.ub.cora.data.json;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class CoraAttributesFluidJsonTest {
    @Test
    public void testEmptyAttributesThrows() {
        var attributesText = CoraAttributesFluidJson.attributes().build().build().toString();
        assertEquals(attributesText, "{}");
    }

    @Test
    public void testOneAttribute() throws FluidJsonException {
        var attributesText = CoraAttributesFluidJson.attributes()
                .attribute("someAttribute", "someValue")
                .build().build().toString();
        assertEquals(attributesText, "{\"someAttribute\":\"someValue\"}");
    }

    @Test
    public void testTwoAttributes() throws FluidJsonException {
        var attributesText = CoraAttributesFluidJson.attributes()
                .attribute("someAttribute", "someValue")
                .attribute("someOtherAttribute", "someOtherValue")
                .build().build().toString();
        assertEquals(attributesText, "{\"someOtherAttribute\":\"someOtherValue\",\"someAttribute\":\"someValue\"}");
    }
}
