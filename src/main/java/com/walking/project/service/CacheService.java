package com.walking.project.service;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/28 3:14 下午
 * @Description:
 */
public interface CacheService {

    Boolean getLock(String lockName, int expireTime);

    Boolean releaseLock(String lockName);
}
