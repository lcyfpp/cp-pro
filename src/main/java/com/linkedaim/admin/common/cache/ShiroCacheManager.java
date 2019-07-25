package com.linkedaim.admin.common.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * 重写Shiro缓存管理器
 * @author zhaoyangyang
 * @date 2019-07-18
 */
public class ShiroCacheManager implements CacheManager {
    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new ShiroCache<K, V>();
    }
}
