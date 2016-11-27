package com.algar.ifuckforwind.util;

import android.support.v4.util.LruCache;

/**
 * Created by algar on 2016-11-27
 */

public class LRUCache {
    private static LRUCache ourInstance = new LRUCache();
    private LruCache<Object, Object> lru;
    private static int cacheSize = 1024 * 1024;        // 1 MiB

    public static LRUCache getInstance() {
        return ourInstance;
    }

    private LRUCache() {
        lru = new LruCache<>(1024);
    }

    public LruCache<Object, Object> getLru() {
        return lru;
    }
}
