package se.uu.ub.cora.data.json;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class CoraFluidJsonTest {

	@Test
	public void testCreateAtomicValue() throws FluidJsonException {
		String coraAtomic = CoraFluidJson.atomic("someName", "someValue")
			.build()
			.toString();
		assertEquals(coraAtomic, "{\"name\":\"someName\",\"value\":\"someValue\"}");
	}

	@Test
	public void testCreateAtomicValueWithRepeatId() throws FluidJsonException {
		String coraAtomic = CoraFluidJson.atomic("someName", "someValue", "someRepeatId")
			.build()
			.toString();
		assertEquals(coraAtomic,
				"{\"repeatId\":\"someRepeatId\",\"name\":\"someName\",\"value\":\"someValue\"}");
	}

	@Test(expectedExceptions = FluidJsonException.class, expectedExceptionsMessageRegExp = "repeatId cannot be null")
	public void testCreateAtomicValueWithNullRepeatId() throws FluidJsonException {
		CoraFluidJson.atomic("someName", "someValue", null)
			.build();
	}

	@Test
	public void testCreateEmptyGroup() throws FluidJsonException {
		String coraGroup = CoraFluidJson.group("someName")
			.build()
			.toString();
		assertEquals(coraGroup, "{\"children\":[],\"name\":\"someName\"}");
	}

	@Test
	public void testCreateEmptyGroupWithAttribute() throws FluidJsonException {
		String coraDataAsText = CoraFluidJson
			.group("someName", CoraFluidJson.attribute("someAttribute", "someAttributeValue"))
			.build()
			.toString();
		String expected = "{\"children\":[],\"name\":\"someName\",\"attributes\":{\"someAttribute\":\"someAttributeValue\"}}";
		assertEquals(coraDataAsText, expected);
	}

	@Test
	public void testCreateEmptyGroupWithNullAttribute() throws FluidJsonException {
		String coraDataAsText = CoraFluidJson.group("someName", (CoraAttributesFluidJson) null)
			.build()
			.toString();
		String expected = "{\"children\":[],\"name\":\"someName\"}";
		assertEquals(coraDataAsText, expected);
	}

	@Test
	public void testCreateEmptyGroupWithTwoAttributes() throws FluidJsonException {
		String coraDataAsText = CoraFluidJson
			.group("someName", CoraFluidJson.attribute("someAttribute", "someAttributeValue")
				.attribute("someOtherAttribute", "someOtherAttributeValue"))
			.build()
			.toString();
		String expected = "{\"children\":[],\"name\":\"someName\",\"attributes\":{\"someOtherAttribute\":\"someOtherAttributeValue\",\"someAttribute\":\"someAttributeValue\"}}";
		assertEquals(coraDataAsText, expected);
	}

	@Test
	public void testCreateEmptyGroupWithRepeatId() throws FluidJsonException {
		String coraDataAsText = CoraFluidJson.group("someName", "someRepeatId")
			.build()
			.toString();
		assertEquals(coraDataAsText,
				"{\"repeatId\":\"someRepeatId\",\"children\":[],\"name\":\"someName\"}");
	}

	@Test
	public void testCreateEmptyGroupWithRepeatIdAndAttribute() throws FluidJsonException {
		String coraDataAsText = CoraFluidJson
			.group("someName", "someRepeatId",
					CoraFluidJson.attribute("someAttribute", "someAttributeValue"))
			.build()
			.toString();
		String expected = "{\"repeatId\":\"someRepeatId\",\"children\":[],\"name\":\"someName\",\"attributes\":{\"someAttribute\":\"someAttributeValue\"}}";
		assertEquals(coraDataAsText, expected);
	}

	@Test
	public void testCreateEmptyGroupWithRepeatIdAndTwoAttributes() throws FluidJsonException {
		String coraDataAsText = CoraFluidJson
			.group("someName", "someRepeatId",
					CoraFluidJson.attribute("someAttribute", "someAttributeValue")
						.attribute("someOtherAttribute", "someOtherAttributeValue"))
			.build()
			.toString();
		String expected = "{\"repeatId\":\"someRepeatId\",\"children\":[],\"name\":\"someName\",\"attributes\":{\"someOtherAttribute\":\"someOtherAttributeValue\",\"someAttribute\":\"someAttributeValue\"}}";
		assertEquals(coraDataAsText, expected);
	}

	@Test
	public void testCreateGroupWithAtomicChild() throws FluidJsonException {
		String coraDataAsText = CoraFluidJson
			.group("someName", CoraFluidJson.atomic("someChildName", "someChildValue"))
			.build()
			.toString();
		String expected = "{\"children\":[{\"name\":\"someChildName\",\"value\":\"someChildValue\"}],\"name\":\"someName\"}";
		assertEquals(coraDataAsText, expected);
	}

	@Test
	public void testCreateGroupWithAtomicChildAndAttribute() throws FluidJsonException {
		String coraDataAsText = CoraFluidJson
			.group("someName", CoraFluidJson.attribute("someAttribute", "someAttributeValue"),
					CoraFluidJson.atomic("someChildName", "someChildValue"))
			.build()
			.toString();
		String expected = "{\"children\":[{\"name\":\"someChildName\",\"value\":\"someChildValue\"}],\"name\":\"someName\",\"attributes\":{\"someAttribute\":\"someAttributeValue\"}}";
		assertEquals(coraDataAsText, expected);
	}

	@Test
	public void testCreateGroupWithRepeatIdAndAttribute() throws FluidJsonException {
		String coraDataAsText = CoraFluidJson
			.group("someName", "someRepeatId",
					CoraFluidJson.attribute("someAttribute", "someAttributeValue"))
			.build()
			.toString();
		String expected = "{\"repeatId\":\"someRepeatId\",\"children\":[],\"name\":\"someName\",\"attributes\":{\"someAttribute\":\"someAttributeValue\"}}";
		assertEquals(coraDataAsText, expected);
	}

	@Test
	public void testCreateGroupWithGroupChild() throws FluidJsonException {
		String coraDataAsText = CoraFluidJson
			.group("someName", CoraFluidJson.group("someChildName"))
			.build()
			.toString();
		String expected = "{\"children\":[{\"children\":[],\"name\":\"someChildName\"}],\"name\":\"someName\"}";
		assertEquals(coraDataAsText, expected);
	}

	@Test
	public void testCreateGroupWithGroupChildAndAttribute() throws FluidJsonException {
		String coraDataAsText = CoraFluidJson
			.group("someName", CoraFluidJson.attribute("someAttribute", "someAttributeValue"))
			.build()
			.toString();
		String expected = "{\"children\":[],\"name\":\"someName\",\"attributes\":{\"someAttribute\":\"someAttributeValue\"}}";
		assertEquals(coraDataAsText, expected);
	}

	@Test
	public void testCreateGroupWithRepeatIdGroupChildAndAttribute() throws FluidJsonException {
		String coraDataAsText = CoraFluidJson
			.group("someName", "someRepeatId",
					CoraFluidJson.attribute("someAttribute", "someAttributeValue"))
			.build()
			.toString();
		String expected = "{\"repeatId\":\"someRepeatId\",\"children\":[],\"name\":\"someName\",\"attributes\":{\"someAttribute\":\"someAttributeValue\"}}";
		assertEquals(coraDataAsText, expected);
	}

	@Test
	public void testCreateGroupWithRepeatIdAtomicChildAndAttribute() throws FluidJsonException {
		String coraDataAsText = CoraFluidJson
			.group("someName", "someRepeatId",
					CoraFluidJson.attribute("someAttribute", "someAttributeValue"),
					CoraFluidJson.atomic("childName", "childValue"))
			.build()
			.toString();
		String expected = "{\"repeatId\":\"someRepeatId\",\"children\":[{\"name\":\"childName\",\"value\":\"childValue\"}],\"name\":\"someName\",\"attributes\":{\"someAttribute\":\"someAttributeValue\"}}";
		assertEquals(coraDataAsText, expected);
	}

	@Test
	public void testCreateGroupWithRepeatIdTwoChildrenAndAttribute() throws FluidJsonException {
		String coraDataAsText = CoraFluidJson
			.group("someName", "someRepeatId",
					CoraFluidJson.attribute("someAttribute", "someAttributeValue"),
					CoraFluidJson.atomic("childName", "childValue"),
					CoraFluidJson.atomic("childSecondName", "childValue"))
			.build()
			.toString();
		String expected = "{\"repeatId\":\"someRepeatId\",\"children\":[{\"name\":\"childName\",\"value\":\"childValue\"},{\"name\":\"childSecondName\",\"value\":\"childValue\"}],\"name\":\"someName\",\"attributes\":{\"someAttribute\":\"someAttributeValue\"}}";
		assertEquals(coraDataAsText, expected);
	}

	@Test
	public void testCreateGroupWithTwoChildrenAndAttribute() throws FluidJsonException {
		String coraDataAsText = CoraFluidJson
			.group("someName", CoraFluidJson.attribute("someAttribute", "someAttributeValue"),
					CoraFluidJson.atomic("childName", "childValue"),
					CoraFluidJson.atomic("childSecondName", "childValue"))
			.build()
			.toString();
		String expected = "{\"children\":[{\"name\":\"childName\",\"value\":\"childValue\"},{\"name\":\"childSecondName\",\"value\":\"childValue\"}],\"name\":\"someName\",\"attributes\":{\"someAttribute\":\"someAttributeValue\"}}";
		assertEquals(coraDataAsText, expected);
	}

	@Test
	public void testCreateGroupWithRepeatIdTwoChildren() throws FluidJsonException {
		String coraDataAsText = CoraFluidJson
			.group("someName", "someRepeatId", CoraFluidJson.atomic("childName", "childValue"),
					CoraFluidJson.atomic("childSecondName", "childValue"))
			.build()
			.toString();
		String expected = "{\"repeatId\":\"someRepeatId\",\"children\":[{\"name\":\"childName\",\"value\":\"childValue\"},{\"name\":\"childSecondName\",\"value\":\"childValue\"}],\"name\":\"someName\"}";
		assertEquals(coraDataAsText, expected);
	}

	@Test
	public void testCreateGroupWithTwoChildren() throws FluidJsonException {
		String coraDataAsText = CoraFluidJson
			.group("someName", CoraFluidJson.atomic("childName", "childValue"),
					CoraFluidJson.atomic("childSecondName", "childValue"))
			.build()
			.toString();
		String expected = "{\"children\":[{\"name\":\"childName\",\"value\":\"childValue\"},{\"name\":\"childSecondName\",\"value\":\"childValue\"}],\"name\":\"someName\"}";
		assertEquals(coraDataAsText, expected);
	}

}
