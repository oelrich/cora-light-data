package se.uu.ub.cora.data.light;

import org.json.JSONObject;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LightDataEngineLoadTest {

    private static final String RECORD_LIST_COLLECTION = "recordList";
    private static final String COLLECTED_DATA_COLLECTION = "collectedData";
    private static final String LINK_LISTS_COLLECTION = "linkLists";
    private static final String CHILDREN = "children";
    private static final String NAME = "name";
    private Map<String, Map<String, Map<String, Set<JSONObject>>>> collections;
    private LightDataEngine lde = new LightDataEngine();
    private Map<Integer, Map<Integer, Map<Integer, Set<LightData>>>> lightCollections;

    @BeforeSuite
    void loadData() {
        loadJsonCollections();
        loadLightData();
    }

    @Test
    void examineCollections() {
        assertEquals(collections.size(), 7);
    }

    @Test
    void examineCollectionsAgain() {
        assertTrue(collections.containsKey("cora"));
    }




    private void loadJsonCollections() {
        collections = new HashMap<>();
        for (var collection : Objects.requireNonNull(getResourceCollections())) {
            addCollectionFromFiles(collection);
        }
    }

    private List<String> getResourceCollections() {
        try (var file = getClass().getResourceAsStream("/collections.txt");
             var inputStream = new InputStreamReader(file);
             var reader = new BufferedReader(inputStream)) {
            return reader.lines().collect(Collectors.toList());
        } catch (Exception ignored) {
        }
        return null;
    }

    private List<String> getResourceFiles() {
        try (var file = getClass().getResourceAsStream("/datafiles.txt");
             var inputStream = new InputStreamReader(file);
             var reader = new BufferedReader(inputStream)) {
            return reader.lines().map(String::trim).filter(item -> !item.isEmpty()).collect(Collectors.toList());
        } catch (Exception ignored) {
        }
        return null;
    }

    private JSONObject readJsonResource(String resourceFileName) {
        try (var file = getClass().getResourceAsStream(resourceFileName);
             var inputStream = new InputStreamReader(file);
             var reader = new BufferedReader(inputStream)) {
            return new JSONObject(reader.lines().collect(Collectors.joining()));
        } catch (Exception ignored) {
        }
        return null;
    }

    private Set<JSONObject> getDataFromObject(JSONObject jsonObject) {
        var documentSet = new HashSet<JSONObject>();
        var documents = jsonObject.getJSONArray(CHILDREN);
        for (int idx = 0; idx < documents.length(); idx++) {
            documentSet.add(documents.getJSONObject(idx));
        }
        return documentSet;
    }

    private void addCollectionFromFiles(String collection) {
        List<String> files = getFilesForCollection(collection);
        collections.put(collection, loadSubCollections(files));
    }

    private List<String> getFilesForCollection(String collection) {
        return Objects.requireNonNull(getResourceFiles()).stream()
                .filter(name -> name.startsWith("/" + collection))
                .collect(Collectors.toList());
    }

    private Map<String, Map<String, Set<JSONObject>>> loadSubCollections(List<String> files) {
        var fileCollections = new HashMap<String, Map<String, Set<JSONObject>>>();
        files.stream().forEach(file -> addFileCollection(file, fileCollections));
        return fileCollections;
    }

    private void addFileCollection(String file, Map<String, Map<String, Set<JSONObject>>> fileCollections) {
        var fileJson = readJsonResource(file);

        var collectionName = fileJson.getString(NAME);
        Map<String, Set<JSONObject>> recordTypeCollection = null;
        if(fileCollections.containsKey(collectionName)) {
            recordTypeCollection = fileCollections.get(collectionName);
        } else {
            recordTypeCollection = new HashMap<>();
        }

        var collectionType = file.split("/")[2].split("_")[0];
        Set<JSONObject> entries;
        if(recordTypeCollection.containsKey(collectionType)) {
            entries = recordTypeCollection.get(collectionType);
        } else {
            entries = new HashSet<>();
        }
        entries.addAll(getDataFromObject(Objects.requireNonNull(fileJson)));
        recordTypeCollection.put(collectionType, entries);

        fileCollections.put(collectionName, recordTypeCollection);
    }

    private void loadLightData() {
        lightCollections = new HashMap<>();
        for(var system: collections.entrySet()) {
            var systemHash = lde.nameHash(system.getKey());
            var systemMap = new HashMap<Integer, Map<Integer, Set<LightData>>>();
            for(var collection: system.getValue().entrySet()) {
                var collectionHash = lde.nameHash(collection.getKey());
                var collectionMap = new HashMap<Integer, Set<LightData>>();
                for(var type: collection.getValue().entrySet()) {
                    var typeHash = lde.nameHash(type.getKey());
                    var typeSet = new HashSet<LightData>();
                    for(var entry: type.getValue()) {
                        typeSet.add(lde.load(entry));
                    }
                    collectionMap.put(typeHash, typeSet);
                }
                systemMap.put(collectionHash, collectionMap);
            }
            lightCollections.put(systemHash, systemMap);
        }
    }
}
