package se.uu.ub.cora.data.hash;

import java.util.HashMap;
import java.util.Map;

public class DeepTreeHash {
    private Map<Integer, String> reverseCache;
    private Map<String, Integer> cache;
    private int maxHash = 0;
    private Map<Character, DeeperTreeHash> hashes;
    public DeepTreeHash() {
        hashes = new HashMap<>();
        cache = new HashMap<>();
        reverseCache = new HashMap<>();
        reverseCache.put(0, "");
    }

    public String string(int hash) {
        if(reverseCache.containsKey(hash)) {
            return reverseCache.get(hash);
        }
        return null;
    }

    class DeeperTreeHash {
        final int hashCode;
        private Map<Character, DeeperTreeHash> hashes;
        DeeperTreeHash(int hashCode) {
            this.hashCode = hashCode;
            hashes = new HashMap<>();
        }
        int hash(String string) {
            if(string.isEmpty()) {
                return hashCode;
            }
            char firstChar = string.charAt(0);
            if(!hashes.containsKey(firstChar)) {
                maxHash++;
                hashes.put(firstChar, new DeeperTreeHash(maxHash));
            }
            return hashes.get(firstChar).hash(string.substring(1));
        }
    }
    public int hash(String string) {
        if(string.isEmpty()) {
            return 0;
        }
        if(cache.containsKey(string)) {
            return cache.get(string);
        }
        char firstChar = string.charAt(0);
        if(!hashes.containsKey(firstChar)) {
            maxHash++;
            hashes.put(firstChar, new DeeperTreeHash(maxHash));
        }
        int hash = hashes.get(firstChar).hash(string.substring(1));
        cache.put(string, hash);
        reverseCache.put(hash, string);
        return hash;
    }

}
