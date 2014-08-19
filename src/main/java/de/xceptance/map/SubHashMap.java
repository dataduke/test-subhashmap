package main.java.de.xceptance.map;

import java.util.*;

public class SubHashMap<S, S1> extends HashMap {

    public SubHashMap() {
        // constructor
    }

    public int size() {
        return super.size();
    }

    public String get(String key) {
        return (String) super.get(key);
    }

    // unique map  (optional requirement)
    // replace old mapping if value already exists
    public Object put(String key, String value) {

        Set<String> keys = this.keySet();
        Iterator<String> keysIterator = keys.iterator();

        while (keysIterator.hasNext()) {
            String keyLookup = keysIterator.next();
            String valueLookup = (String) super.get(keyLookup);
            if (valueLookup == value) {
                this.remove(keyLookup);
                break;
            }
        }
        return super.put(key,value);
    }

    public String remove(String key) {
       return (String) super.remove(key);
    }
}
