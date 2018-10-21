package se.uu.ub.cora.data.light;

import org.json.JSONArray;
import org.json.JSONObject;

import se.uu.ub.cora.data.hash.DeepTreeHash;

import java.util.HashMap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LightDataEngine {
    private static final String REPEAT_ID = "repeatId";
    private static final String ATTRIBUTES = "attributes";
    private static final String VALUE = "value";
    private DeepTreeHash nameHash = new DeepTreeHash();
    private DeepTreeHash valueHash = new DeepTreeHash();
    private DeepTreeHash otherHash = new DeepTreeHash();
    private static final String CHILDREN = "children";
    private static final String NAME = "name";
    private Map<Integer, Set<LightGroup>> collections = new HashMap<>();

    public Set<LightGroup> list(String type) {
        var typeHash = nameHash.hash(type);
        return collections.get(typeHash);
    }

    private LightGroup loadGroup(JSONObject group) {
        var name = (String) group.get(NAME);
        var nameValue = nameHash.hash(name);
        int repeatIdValue = getRepeatIdValue(group);
        var attributes = getAttributes(group);
        var children = getChildren(group.getJSONArray(CHILDREN));
        return new LightGroup(nameValue, repeatIdValue, attributes, children);
    }
    private LightData loadData(JSONObject json) {
        if(json.has(CHILDREN)) {
            return loadGroup(json);
        }
        return loadAtom(json);
    }

    private LightAtom loadAtom(JSONObject atom) {
        var name = (String) atom.get(NAME);
        var nameValue = nameHash.hash(name);
        int repeatIdValue = getRepeatIdValue(atom);
        var value = (String) atom.get(VALUE);
        var valueValue = valueHash.hash(value);
        return new LightAtom(nameValue, repeatIdValue, valueValue);
    }

    private LightData[] getChildren(JSONArray jsonArray) {
        if(jsonArray.length() > 0) {
            var children = new LightData[jsonArray.length()];
            for (int idx = 0; idx < jsonArray.length(); idx++) {
                children[idx] = loadData(jsonArray.getJSONObject(idx));
            }
            return children;
        }
        return null;
    }

    private Map<Integer, Integer> getAttributes(JSONObject group) {
        Map<Integer,Integer> attributeMap = new HashMap<>();
        if(group.has(ATTRIBUTES)) {
            JSONObject attributes = group.getJSONObject(ATTRIBUTES);
            for(var key: attributes.keySet()) {
                var keyHash = otherHash.hash(key);
                String value = attributes.getString(key);
                attributeMap.put(keyHash, otherHash.hash(value));
            }
        }
        return attributeMap;
    }

    public LightGroup load(JSONObject group) {
        if(group.has(CHILDREN)) {
            var lightGroup = loadGroup(group);
            if(collections.containsKey(lightGroup.name))
            {
                var typeGroup = collections.get(lightGroup.name);
                typeGroup.add(lightGroup);
                collections.put(lightGroup.name, typeGroup);
            } else {
                var lightGroupSet = new HashSet<LightGroup>();
                lightGroupSet.add(lightGroup);
                collections.put(lightGroup.name, lightGroupSet);
            }
           return loadGroup(group);
        }
        return null;
    }

    private int getRepeatIdValue(JSONObject emptyGroup) {
        var repeatIdValue = 0;
        if(emptyGroup.has(REPEAT_ID)) {
            var repeatId = (String) emptyGroup.get(REPEAT_ID);
            repeatIdValue = otherHash.hash(repeatId);
        }
        return repeatIdValue;
    }

    public int nameHash(String name) {
        return nameHash.hash(name);
    }

    public int valueHash(String name) {
        return valueHash.hash(name);
    }

    public int otherHash(String value) {
        return otherHash.hash(value);
    }
}
