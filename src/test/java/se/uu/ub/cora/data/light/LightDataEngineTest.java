package se.uu.ub.cora.data.light;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static se.uu.ub.cora.data.json.CoraFluidJson.atomic;
import static se.uu.ub.cora.data.json.CoraFluidJson.attribute;
import static se.uu.ub.cora.data.json.CoraFluidJson.group;

import java.util.Map;
import java.util.Set;

import org.testng.annotations.Test;

import se.uu.ub.cora.data.json.FluidJsonException;

public class LightDataEngineTest {

	@Test
	void testEmptyGroup() throws FluidJsonException {
		var lde = new LightDataEngine();

		String someName = "someName";
		var emptyGroup = group(someName).build();

		var lightGroup = lde.load(emptyGroup);

		var nameHash = lde.nameHash(someName);

		assertEquals(lightGroup.name, nameHash);
		assertEquals(lightGroup.repeatId, 0);
		assertEquals(lightGroup.attributes, Map.of());
		assertNull(lightGroup.children);
	}

	@Test
	void testEmptyGroupWithRepeatId() throws FluidJsonException {
		var lde = new LightDataEngine();
		String someName = "someName";
		String someRepeatId = "someRepeatId";
		var emptyGroup = group(someName, someRepeatId).build();

		LightGroup lightGroup = lde.load(emptyGroup);
		assertEquals(lightGroup.name, lde.nameHash(someName));
		assertEquals(lightGroup.repeatId, lde.otherHash(someRepeatId));
		assertEquals(lightGroup.attributes, Map.of());
		assertNull(lightGroup.children);
	}

	@Test
	void testEmptyGroupWithAttributes() throws FluidJsonException {
		var lde = new LightDataEngine();
		String someName = "someName";
		String attributeName = "someAttributeName";
		String attributeValue = "someAttributeValue";
		var emptyGroup = group(someName, attribute(attributeName, attributeValue)).build();

		LightGroup lightGroup = lde.load(emptyGroup);

		int aN = lde.otherHash(attributeName);
		int aV = lde.otherHash(attributeValue);

		assertEquals(lightGroup.name, lde.nameHash(someName));
		assertEquals(lightGroup.repeatId, 0);
		assertEquals(lightGroup.attributes, Map.of(aN, aV));
		assertNull(lightGroup.children);
	}

	@Test
	void testGroupWithChild() throws FluidJsonException {
		var lde = new LightDataEngine();
		String someName = "someName";
		String childName = "someChildName";
		String childValue = "someChildValue";
		var emptyGroup = group(someName, atomic(childName, childValue)).build();

		LightGroup lightGroup = lde.load(emptyGroup);

		int cN = lde.nameHash(childName);
		int cV = lde.valueHash(childValue);

		assertEquals(lightGroup.name, lde.nameHash(someName));
		assertEquals(lightGroup.repeatId, 0);
		assertEquals(lightGroup.attributes, Map.of());
		assertEquals(lightGroup.children.length, 1);
		assertEquals(lightGroup.children[0].name, cN);
		assertEquals(((LightAtom) lightGroup.children[0]).value, cV);
	}

	@Test
	void testGroupWithDeepChild() throws FluidJsonException {
		var lde = new LightDataEngine();
		String someName = "someName";
		String childName = "someChildName";
		String grandChildName = "someGrandChildName";
		String childValue = "someChildValue";
		var emptyGroup = group(someName, group(childName, atomic(grandChildName, childValue)))
			.build();

		LightGroup lightGroup = lde.load(emptyGroup);

		int cN = lde.nameHash(childName);
		int gcN = lde.nameHash(grandChildName);
		int cV = lde.valueHash(childValue);

		assertEquals(lightGroup.name, lde.nameHash(someName));
		assertEquals(lightGroup.repeatId, 0);
		assertEquals(lightGroup.attributes, Map.of());
		assertEquals(lightGroup.children.length, 1);
		assertEquals(lightGroup.children[0].name, cN);
		assertEquals(((LightAtom) ((LightGroup) lightGroup.children[0]).children[0]).name, gcN);
		assertEquals(((LightAtom) ((LightGroup) lightGroup.children[0]).children[0]).value, cV);
	}

	@Test
	void testGroup() throws FluidJsonException {
		var lde = new LightDataEngine();
		String type = "first";
		var first = group(type).build();
		var loaded = lde.load(first);

		Set<LightGroup> items = lde.list(type);
		assertEquals(items.iterator()
			.next().name, loaded.name);
		assertEquals(items.size(), 1);
	}

	@Test
	void testAtom() throws FluidJsonException {
		var lde = new LightDataEngine();
		var first = atomic("should", "beNull").build();
		assertNull(lde.load(first));
	}
}
