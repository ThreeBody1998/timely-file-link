package com.util;

import com.pojo.custom.CacheEntry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * description 缓存操作类型
 * @author 周建泽
 * @date 2023/10/13
 */
@Component
public class CacheManagerUtil<K,V> {
    private final Map<K, CacheEntry<V>> cacheMap;
    private final ScheduledExecutorService scheduler;
    /**
     * 默认过期时间 3小时
     */
    public static long TTL=1000*60*60*3L;

    public CacheManagerUtil() {
        cacheMap = new ConcurrentHashMap<>();
        scheduler = Executors.newScheduledThreadPool(1);
    }

    /**
     * 存值
     * @param key   键
     * @param value 值
     * @param expirationTimeMillis  过期时间
     */
    public void put(K key, V value, long expirationTimeMillis) {
        expirationTimeMillis += System.currentTimeMillis();
        CacheEntry<V> entry = new CacheEntry<>(value, expirationTimeMillis);
        cacheMap.put(key, entry);
        // 定时任务，在过期时间后自动销毁缓存条目
        scheduler.schedule(() -> cacheMap.remove(key), expirationTimeMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * 根据键取值
     * @param key   键
     * @return  值
     */
    public V get(K key){
        CacheEntry<V> entry = cacheMap.get(key);
        if (entry != null && !entry.isExpired()) {
            return entry.getValue();
        }
        return null;
    }

    /**
     * 根据建删除对应的键值对
     * @param key   键
     */
    public void remove(K key) {
        cacheMap.remove(key);
    }

    /**
     * 获取缓存键列表
     * @return  缓存键列表
     */
    public List<K> getKeys(){
        return new ArrayList<>(cacheMap.keySet());
    }
}
