package com.walking.project.common.authorization;

import com.walking.project.utils.JedisUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/27 9:51 上午
 * @Description:
 */
@Service
public class ShiroCacheManager implements CacheManager {

    @Autowired
    JedisUtils jedisUtils;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new ShiroCache<K,V>(jedisUtils);
    }
}
