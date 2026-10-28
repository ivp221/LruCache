import IshaVishakhaPramod.LruCache.LruCache;
import org.junit.Test;

import static org.junit.Assert.*;


public class LruCacheTest {

    @Test
    public void missReturnsNull() {
        assertNull(new LruCache<String, Integer>(2).get("x"));
    }

    @Test
    public void evictsLeastRecentlyUsed() {
        var c = new LruCache<String, Integer>(2);
        c.put("a", 1); c.put("b", 2); c.put("c", 3);
        assertNull(c.get("a"));
        assertEquals((Integer) 2, c.get("b"));
        assertEquals((Integer) 3, c.get("c"));
    }

    @Test
    public void getRefreshesRecency() {
        var c = new LruCache<String, Integer>(2);
        c.put("a", 1); c.put("b", 2);
        c.get("a");              // b is now LRU
        c.put("c", 3);
        assertEquals((Integer) 1, c.get("a"));
        assertNull(c.get("b"));
    }

    @Test
    public void updateDoesNotGrowAndRefreshes() {
        var c = new LruCache<String, Integer>(2);
        c.put("a", 1); c.put("b", 2);
        c.put("a", 10);          // a is MRU, b is LRU
        c.put("c", 3);
        assertEquals(2, c.size());
        assertEquals((Integer) 10, c.get("a"));
        assertNull(c.get("b"));
    }

    @Test
    public void capacityOne() {
        var c = new LruCache<String, Integer>(1);
        c.put("a", 1); c.put("b", 2);
        assertNull(c.get("a"));
        assertEquals((Integer) 2, c.get("b"));
    }



    @Test
    public void rejectsBadInput() {
        assertThrows(IllegalArgumentException.class, () -> new LruCache<String, Integer>(0));
        var c = new LruCache<String, Integer>(1);
        assertThrows(NullPointerException.class, () -> c.put(null, 1));
        assertThrows(NullPointerException.class, () -> c.put("a", null));
    }
}
