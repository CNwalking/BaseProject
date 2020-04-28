package com.walking.project.common.authorization;

import com.walking.project.utils.JWTUtils;
import com.walking.project.utils.JedisUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.walking.project.common.authorization.SecurityConsts.*;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/27 9:52 上午
 * @Description:
 */
public class ShiroCache<K,V> implements Cache<K,V> {

    private JedisUtils jedisUtils;

    public ShiroCache(JedisUtils jedisUtils) {
        this.jedisUtils = jedisUtils;
    }

    /**
     * 获取缓存
     * @param key
     * @return
     * @throws CacheException
     */
    @Override
    public Object get(Object key) throws CacheException{
        String tempKey= this.getKey(key);
        Object result=null;
        if(jedisUtils.exists(tempKey)){
            result = jedisUtils.getObject(tempKey);
        }
        return result;
    }

    /**
     * 保存缓存
     * @param key
     * @param value
     * @return
     * @throws CacheException
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {
        String tempKey= this.getKey(key);
        jedisUtils.saveObject(tempKey, value,TOKEN_EXPIRE_TIME);
        return value;
    }

    /**
     * 移除缓存
     * @param key
     * @return
     * @throws CacheException
     */
    @Override
    public Object remove(Object key) throws CacheException {
        String tempKey= this.getKey(key);
        if(jedisUtils.exists(tempKey)){
            jedisUtils.delKey(tempKey);
        }
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 20;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        Set keys = this.keys();
        List<V> values = new ArrayList<>();
        for (Object key : keys) {
            values.add((V)jedisUtils.getObject(this.getKey(key)));
        }
        return values;
    }

    /**
     * 缓存的key名称获取为shiro:cache:account
     * @param key
     * @return
     */
    private String getKey(Object key) {
        return SecurityConsts.PREFIX_SHIRO_CACHE + JWTUtils.getAccount(key.toString());
    }
}
