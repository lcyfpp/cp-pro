package com.linkedaim.admin.common.cache;

import com.linkedaim.admin.common.constant.SystemConstant;
import com.linkedaim.admin.common.util.JedisUtil;
import com.linkedaim.admin.common.util.JwtUtil;
import com.linkedaim.admin.common.util.SerializableUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 重写Shiro的Cache保存读取
 * @author zhaoyangyang
 * @date 2019-07-18
 */
@Component
public class ShiroCache<K, V> implements Cache<K, V> {

    private static String shiroCacheExpireTime;

    @Value("${linkedaim.shiroCacheExpireTime}")
    public void setShiroCacheExpireTime(String shiroCacheExpireTime) {
        ShiroCache.shiroCacheExpireTime = shiroCacheExpireTime;
    }

    /**
     * 缓存的key名称获取为shiro:cache:account
     * @param key
     * @return java.lang.String
     * @author zhaoyangyang
     * @date 2018/9/4 18:33
     */
    private String getKey(Object key) {
        return SystemConstant.PREFIX_SHIRO_CACHE + JwtUtil.getClaim(key.toString(), SystemConstant.ACCOUNT);
    }

    /**
     * 获取缓存
     */
    @Override
    public Object get(Object key) throws CacheException {
        if (!JedisUtil.exists(this.getKey(key))) {
            return null;
        }
        return JedisUtil.getObject(this.getKey(key));
    }

    /**
     * 保存缓存
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {
        // 设置Redis的Shiro缓存
        return JedisUtil.setObject(this.getKey(key), value, Integer.parseInt(shiroCacheExpireTime));
    }

    /**
     * 移除缓存
     */
    @Override
    public Object remove(Object key) throws CacheException {
        if (!JedisUtil.exists(this.getKey(key))) {
            return null;
        }
        JedisUtil.delKey(this.getKey(key));
        return null;
    }

    /**
     * 清空所有缓存
     */
    @Override
    public void clear() throws CacheException {
        JedisUtil.getJedis().flushDB();
    }

    /**
     * 缓存的个数
     */
    @Override
    public int size() {
        Long size = JedisUtil.getJedis().dbSize();
        return size.intValue();
    }

    /**
     * 获取所有的key
     */
    @Override
    public Set keys() {
        Set<byte[]> keys = JedisUtil.getJedis().keys(new String("*").getBytes());
        Set<Object> set = new HashSet<Object>();
        for (byte[] bs : keys) {
            set.add(SerializableUtil.unserializable(bs));
        }
        return set;
    }

    /**
     * 获取所有的value
     */
    @Override
    public Collection values() {
        Set keys = this.keys();
        List<Object> values = new ArrayList<Object>();
        for (Object key : keys) {
            values.add(JedisUtil.getObject(this.getKey(key)));
        }
        return values;
    }
}
