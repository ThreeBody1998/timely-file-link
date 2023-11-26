package com.pojo.custom;

import lombok.AllArgsConstructor;

/**
 * description 缓存对象
 * @author 周建泽
 * @date 2023/10/13
 */
@AllArgsConstructor
public class CacheEntry<V> {
    //存储数据
    private final V value;
    //过期时间
    private final long expirationTimeMillis;

    /**
     * 是否过期
     * @return  布尔值
     */
    public boolean isExpired() {
        return System.currentTimeMillis() > expirationTimeMillis;
    }

    public V getValue() {
        return value;
    }
}
