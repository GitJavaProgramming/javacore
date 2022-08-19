package org.pp.cn.hutool.cache;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.FIFOCache;
import cn.hutool.cache.impl.LFUCache;

public class CacheTest {
    public static void main(String[] args) {
        FIFOCache<String, Object> fifoCache = CacheUtil.newFIFOCache(10);
        LFUCache<Object, Object> lfuCache = CacheUtil.newLFUCache(10);
        fifoCache.forEach(t-> System.out.println(t));
    }
}
