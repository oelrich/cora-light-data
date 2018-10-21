package se.uu.ub.cora.data.json;

import java.util.Arrays;

import org.json.JSONObject;

public class CoraFluidJson {
	private static final String NAME_IN_DATA = "name";
	private static final String VALUE = "value";
	private static final String CHILDREN = "children";
	private static final String REPEAT_ID = "repeatId";
	private static final String ATTRIBUTES = "attributes";

	private FluidJson baseJson;

	private CoraFluidJson(FluidJson base) {
		baseJson = base;
	}

	public static CoraFluidJson atomic(String nameInData, String value) throws FluidJsonException {
		return new CoraFluidJson(getDataElementJson(nameInData).property(VALUE, value));
	}

	private static FluidJson getDataElementJson(String nameInData) throws FluidJsonException {
		return getFluidJsonWithNameInData(nameInData);
	}

	private static FluidJson getFluidJsonWithNameInData(String nameInData)
			throws FluidJsonException {
		return FluidJson.json()
			.property(NAME_IN_DATA, nameInData);
	}

	public static CoraFluidJson atomic(String nameInData, String value, String repeatId)
			throws FluidJsonException {
		return new CoraFluidJson(getDataElementJson(nameInData, repeatId).property(VALUE, value));
	}

	private static FluidJson getDataElementJson(String nameInData, String repeatId)
			throws FluidJsonException {
		var fluidJson = getFluidJsonWithNameInData(nameInData);
		possiblyAddRepeatId(repeatId, fluidJson);
		return fluidJson;
	}

	private static void possiblyAddRepeatId(String repeatId, FluidJson fluidJson)
			throws FluidJsonException {
		if (repeatId != null) {
			fluidJson.property(REPEAT_ID, repeatId);
		} else {
			throw new FluidJsonException("repeatId cannot be null");
		}
	}

	public static CoraFluidJson group(String nameInData) throws FluidJsonException {
		return new CoraFluidJson(getDataElementJson(nameInData).array(CHILDREN));
	}

	public static CoraFluidJson group(String nameInData, CoraAttributesFluidJson attributes)
			throws FluidJsonException {
		return new CoraFluidJson(getDataElementJson(nameInData, attributes).array(CHILDREN));
	}

	private static FluidJson getDataElementJson(String nameInData,
			CoraAttributesFluidJson attributes) throws FluidJsonException {
		var fluidJson = getFluidJsonWithNameInData(nameInData);
		possiblyAddAttributes(attributes, fluidJson);
		return fluidJson;
	}

	private static void possiblyAddAttributes(CoraAttributesFluidJson attributes,
			FluidJson fluidJson) throws FluidJsonException {
		if (attributes != null) {
			fluidJson.property(ATTRIBUTES, attributes.build());
		}
	}

	public static CoraFluidJson group(String nameInData, String repeatId)
			throws FluidJsonException {
		return new CoraFluidJson(getDataElementJson(nameInData, repeatId).array(CHILDREN));
	}

	public static CoraFluidJson group(String nameInData, String repeatId,
			CoraAttributesFluidJson attributes) throws FluidJsonException {
		return new CoraFluidJson(
				getDataElementJson(nameInData, repeatId, attributes).array(CHILDREN));
	}

	private static FluidJson getDataElementJson(String nameInData, String repeatId,
			CoraAttributesFluidJson attributes) throws FluidJsonException {
		var fluidJson = getFluidJsonWithNameInData(nameInData);
		possiblyAddRepeatId(repeatId, fluidJson);
		possiblyAddAttributes(attributes, fluidJson);
		return fluidJson;
	}

	public static CoraFluidJson group(String nameInData, CoraFluidJson... children)
			throws FluidJsonException {
		var childData = getCoraFluidJsonArrayFromChildren(children);
		return new CoraFluidJson(getDataElementJson(nameInData).array(CHILDREN, childData));
	}

	public static CoraFluidJson group(String nameInData, String repeatId, CoraFluidJson... children)
			throws FluidJsonException {
		var childData = getCoraFluidJsonArrayFromChildren(children);
		return new CoraFluidJson(
				getDataElementJson(nameInData, repeatId).array(CHILDREN, childData));
	}

	public static CoraFluidJson group(String nameInData, CoraAttributesFluidJson attributes,
			CoraFluidJson... children) throws FluidJsonException {
		var childData = getCoraFluidJsonArrayFromChildren(children);
		return new CoraFluidJson(
				getDataElementJson(nameInData, attributes).array(CHILDREN, childData));
	}

	public static CoraFluidJson group(String nameInData, String repeatId,
			CoraAttributesFluidJson attributes, CoraFluidJson... children)
			throws FluidJsonException {
		var childData = getCoraFluidJsonArrayFromChildren(children);
		return new CoraFluidJson(
				getDataElementJson(nameInData, repeatId, attributes).array(CHILDREN, childData));
	}

	private static Object[] getCoraFluidJsonArrayFromChildren(CoraFluidJson[] children) {
		return Arrays.stream(children)
			.map(elt -> elt.baseJson)
			.toArray();
	}

	public static CoraAttributesFluidJson attribute(String name, String value)
			throws FluidJsonException {
		return CoraAttributesFluidJson.attributes()
			.attribute(name, value);
	}

	public JSONObject build() {
		return baseJson.build();
	}
}
