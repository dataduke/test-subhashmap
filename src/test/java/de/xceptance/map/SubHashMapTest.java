package test.java.de.xceptance.map;

import main.java.de.xceptance.map.SubHashMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class SubHashMapTest {

    private final SubHashMap<String, String> mapEmpty = new SubHashMap<String, String>();
    private final SubHashMap<String, String> mapOne = new SubHashMap<String, String>();
    private final SubHashMap<String, String> mapThree = new SubHashMap<String, String>();
    private final SubHashMap<String, String> mapFive = new SubHashMap<String, String>();

    @Before
    public void addMappings() {
        mapOne.put("Rene", "CEO");

        mapThree.put("Rene", "CEO");
        mapThree.put("Ronny", "CTO");
        mapThree.put("Simone", "QAM");

        mapFive.put("Rene", "CEO");
        mapFive.put("Ronny", "CTO");
        mapFive.put("Simone", "QAM");
        mapFive.put("Michael", "Developer001");
        mapFive.put("Jens", "Developer002");
    }

    @After
    public void clearAll() {
        mapEmpty.clear();
        mapOne.clear();
        mapThree.clear();
        mapFive.clear();
    }

    @Test
    public void testSizeStandard() {
        assertEquals(1, mapOne.size());
        assertEquals(3, mapThree.size());
        assertEquals(5, mapFive.size());
    }

    // size increased by one
    @Test
    public void testSizeIncrease() {
        int oldSize = mapThree.size();
        mapThree.put("Foo","BAR");
        int newSize = mapThree.size();
        assertEquals(oldSize + 1, newSize);
    }

    // size decreased by one
    @Test
    public void testSizeDecrease() {
        int oldSize = mapThree.size();
        mapThree.remove("Ronny");
        int newSize = mapThree.size();
        assertEquals(oldSize - 1, newSize);
    }

    // size is empty
    @Test
    public void testSizeEmpty() {
        assertEquals(0, mapEmpty.size());
    }


    // map gets emptied
    @Test
    public void testSizeCleared() {
        mapFive.clear();
        assertTrue(mapFive.isEmpty());
    }

    // existing keys
    @Test
    public void testGetStandard() {
        mapEmpty.put("Rene", "CEO");
        String actual = (String) mapEmpty.get("Rene");
        assertEquals("CEO", actual);
    }

    // not existing key
    @Test
    public void testGetKeyMissing() {
        assertNull(mapEmpty.get("NotAKey"));
    }

    // add mapping with case-sensitive values
    @Test
    public void testGetNotIgnoreCase() {
        mapEmpty.put("rene","CEO");
        assertNotNull(mapEmpty.get("rene"));
        assertNull(mapEmpty.get("RENE"));
    }

    // add mapping increases size by one
    @Test
    public void testPutStandard() {
        mapEmpty.put("Rene","CEO");
        int expectedSize = 1;
        assertEquals(expectedSize, mapEmpty.size());
    }

    // null key gets not added
    @Test
    public void testPutNullKey() {
        try {
            Object o = null;
            mapEmpty.put(o, "CFO");
        } catch (NullPointerException e){
            // expected
        }
    }

    // null value gets not added
    @Test
    public void testPutNullValue() {
        try {
            Object o = null;
            mapEmpty.put("Dirk", o);
        } catch (NullPointerException e){
            // expected
        }
    }

    // key already exists and value gets overwritten
    @Test
    public void testPutOverwriteKey() {
        mapEmpty.put("Rene","CEO");
        String oldValue = mapEmpty.get("Rene");
        mapEmpty.put("Rene","CFO");
        String newValue = mapEmpty.get("Rene");
        assertNotSame(oldValue, newValue);
    }

    // value already exists and key gets overwritten
    @Test
    public void testPutOverwriteValue() {
        mapEmpty.put("Rene","CEO");
        mapEmpty.put("Sara","CEO");
        assertNull(mapEmpty.get("Rene"));
        assertEquals("CEO", mapEmpty.get("Sara"));
    }


    // add mapping with case-sensitive values
    @Test
    public void testPutNotIgnoreCase() {
        mapEmpty.put("Rene","CEO");
        assertNotSame("ceo", mapEmpty.get("Rene"));
    }

    // remove a mapping
    @Test
    public void testRemoveStandard() {
        int oldSize = mapThree.size();
        mapThree.remove("Ronny");
        int newSize = mapThree.size();
        assertNull(mapThree.get("Ronny"));
        assertEquals(oldSize - 1 , newSize);
    }

    // remove all keys
    @Test
    public void testRemoveAll(){
        Set<String> keys = mapFive.keySet();
        List<String> tempKeys = new ArrayList<String>();

        for(String key : keys)
            tempKeys.add(key);
        keys.clear();

        for(String key : tempKeys)
            mapFive.remove(key);
        tempKeys.clear();

        assertEquals(0, mapFive.size());
    }

    // remove with case sensitivity
    @Test
    public void testRemoveNotIgnoreCase(){
        mapEmpty.put("rene", "CEO");
        assertNull(mapEmpty.remove("RENE"));
        assertNull(mapEmpty.remove("Rene"));
        assertNotNull(mapEmpty.get("rene"));
    }
}
