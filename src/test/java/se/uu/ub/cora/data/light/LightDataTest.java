package se.uu.ub.cora.data.light;

import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.*;

public class LightDataTest {
    @Test
    void testAtomWithNameValue() {
        LightAtom lightData = new LightAtom(1,2);
        assertEquals(lightData.name, 1);
        assertEquals(lightData.repeatId, 0);
        assertTrue(lightData.attributes.isEmpty());
        assertEquals(lightData.value, 2);
    }

    @Test
    void testInit() {
        LightAtom lightData = new LightAtom(1, 3,2);
        assertEquals(lightData.name, 1);
        assertEquals(lightData.repeatId, 3);
        assertTrue(lightData.attributes.isEmpty());
        assertEquals(lightData.value, 2);
    }

   @Test
    void testInitGroup() {
        LightGroup lightData = new LightGroup(1);
        assertEquals(lightData.name, 1);
        assertEquals(lightData.repeatId, 0);
        assertTrue(lightData.attributes.isEmpty());
       assertNull(lightData.children);
    }

    @Test
    void testInitGroupNameRepeatId() {
        LightGroup lightData = new LightGroup(1, 1);
        assertEquals(lightData.name, 1);
        assertEquals(lightData.repeatId, 1);
        assertTrue(lightData.attributes.isEmpty());
        assertNull(lightData.children);
    }

    @Test
    void testInitGroupNameRepeatIdAttributesDataWithChildren() {
        LightGroup lightData = new LightGroup(1, 1, Map.of(1,2), new LightData[1]);
        assertEquals(lightData.name, 1);
        assertEquals(lightData.repeatId, 1);
        assertEquals((int) lightData.attributes.get(1), 2);
        assertEquals(lightData.children.length, 1);
    }

    @Test
    void testInitGroupNameRepeatIdAttributesData() {
        LightGroup lightData = new LightGroup(1, 1, Map.of(1,2));
        assertEquals(lightData.name, 1);
        assertEquals(lightData.repeatId, 1);
        assertEquals((int) lightData.attributes.get(1), 2);
        assertNull(lightData.children);
    }

}
