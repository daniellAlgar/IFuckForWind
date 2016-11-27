package com.algar.ifuckforwind;

import com.algar.ifuckforwind.util.LRUCache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;


/**
 * Created by algar on 2016-11-27
 */

@RunWith(JUnit4.class)
public class LRUCacheTest {

    LRUCache cache = LRUCache.getInstance();

    @Test
    public void LRUCache_should_be_singleton() {
        assertEquals(cache, LRUCache.getInstance());
    }

    @Test
    public void cache_simple_string() {
        String key = "key";
        String val = "val";

        cache.getLru().put(key, val);

        assertEquals(val, cache.getLru().get(key));
    }

    @Test
    public void cache_string_array() {
        String[] keys = {"foo_key", "bar_key", "zoo_key"};
        String[] vals = {"foo", "bar", "zoo"};

        cache.getLru().put(keys, vals);

        assertEquals(vals, cache.getLru().get(keys));
    }

    @Test
    public void overwrite_cached_key_val_string() {
        String key = "key";
        String val_first = "first val";
        String val_second = "second val";

        cache.getLru().put(key, val_first);
        cache.getLru().put(key, val_second);

        assertEquals(val_second, cache.getLru().get(key));
    }

    @Test
    public void overwrite_cached_key_val_string_array() {
        String key = "key";
        String[] vals_first = {"foo", "bar"};
        String[] vals_second = {"fooz", "barz", "zooz"};

        cache.getLru().put(key, vals_first);
        cache.getLru().put(key, vals_second);

        assertEquals(vals_second, cache.getLru().get(key));
    }
}
