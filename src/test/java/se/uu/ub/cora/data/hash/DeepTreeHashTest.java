package se.uu.ub.cora.data.hash;

import org.json.JSONObject;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DeepTreeHashTest {
    private final static String LINK_LIST_CORA = "/cora/linkLists_cora.json";
    private JSONObject linkListsCoraJson;
    private List<String> names;
    private List<String> values;

    @BeforeSuite
    void loadJsonAndGetUniqueNamesAndGetUniqueValues() {
        try(var file = getClass().getResourceAsStream(LINK_LIST_CORA);
            var inputStream = new InputStreamReader(file);
            var reader = new BufferedReader(inputStream)) {
            linkListsCoraJson = new JSONObject(reader.lines().collect(Collectors.joining()));
        } catch(Exception ignored) {}
        names = getPropertyList(linkListsCoraJson, "name");
        values = getPropertyList(linkListsCoraJson, "value");
    }

    private List<String> getPropertyList(JSONObject jsonObject, String property) {
        Set<String> properties = new HashSet<>();
        if(jsonObject.has(property)) {
            properties.add(jsonObject.getString(property));
        }
        if(jsonObject.has("children")) {
            var kids = jsonObject.getJSONArray("children");
            for(var kid : kids) {
                properties.addAll(getPropertyList((JSONObject) kid, property));
            }
        }
        return new ArrayList<>(properties);
    }

    @Test
    void examineJson() {
        assertEquals(names.size(), 3723);
        assertEquals(values.size(), 5315);
    }

    @Test
    void examinePerformance() {
        var dth = new DeepTreeHash();
        names.forEach(dth::hash);
        values.forEach(dth::hash);
    }

    @Test
    void examinePerformance2() {
        var dth = new DeepTreeHash();
        names.forEach(dth::hash);
        values.forEach(dth::hash);
        names.forEach(dth::hash);
        values.forEach(dth::hash);
    }

    @Test
    void testRobustness() {
        var dth = new DeepTreeHash();
        names.forEach(dth::hash);
        values.forEach(dth::hash);

        assertTrue(names.stream().allMatch(name -> name.equals(dth.string(dth.hash(name)))));
        assertTrue(values.stream().allMatch(value -> value.equals(dth.string(dth.hash(value)))));
    }

    @Test
    void testReverse() {
        var dth =  new DeepTreeHash();
        assertEquals(dth.string(dth.hash("bob")), "bob");
    }

    @Test
    void testEmptyStringIsZero() {
        var dth =  new DeepTreeHash();
        long hash = dth.hash("");
        assertEquals(hash, 0);
    }

    @Test(expectedExceptions = NullPointerException.class)
    void testNullThrows() {
        var dth =  new DeepTreeHash();
        long hash = dth.hash(null);
        assertEquals(hash, 0);
    }

    @Test
    void testHashSingleCharacter() {
        var dth =  new DeepTreeHash();
        var hash = dth.hash("w");
        assertEquals(hash, 1);
    }

    @Test
    void testHashTwoSingleCharacter() {
        var dth =  new DeepTreeHash();
        assertEquals(dth.hash("w"), 1);
        assertEquals(dth.hash("o"), 2);
    }

    @Test
    void testHashTwoSingleCharacterAndTheFirstShouldStayTheSame() {
        var dth =  new DeepTreeHash();
        assertEquals(dth.hash("w"), 1);
        assertEquals(dth.hash("o"), 2);
        assertEquals(dth.hash("w"), 1);
    }

    @Test
    void testHashDoubleCharacter() {
        var dth =  new DeepTreeHash();

        assertEquals(dth.hash("w"), 1);
        assertEquals(dth.hash("wh"), 2);
    }

    @Test
    void testHashDeeperStrings() {
        var dth =  new DeepTreeHash();
        dth.hash("what");
        dth.hash("when");
        assertEquals(dth.hash("w"), 1);
        assertEquals(dth.hash("wh"), 2);
        assertEquals(dth.hash("wha"), 3);
        assertEquals(dth.hash("what"), 4);
        assertEquals(dth.hash("whe"), 5);
        assertEquals(dth.hash("when"), 6);
        assertEquals(dth.hash(""), 0);
    }

}
