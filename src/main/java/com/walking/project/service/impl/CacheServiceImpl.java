package com.walking.project.service.impl;

import com.walking.project.common.ProjectConstant;
import com.walking.project.service.CacheService;
import com.walking.project.utils.JedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/28 3:15 下午
 * @Description:
 */
@Slf4j
public class CacheServiceImpl implements CacheService {

    @Autowired
    JedisUtils jedisUtils;

    /**
     * 获取redis中key的锁，乐观锁实现
     * @param lockName
     * @param expireTime 锁的失效时间
     * @return
     */
    @Override
    public Boolean getLock(String lockName, int expireTime) {
        Boolean result = Boolean.FALSE;
        try {
            boolean isExist = jedisUtils.exists(lockName);
            if(!isExist){
                jedisUtils.getSeqNext(lockName,0);
                jedisUtils.expire(lockName, expireTime <= 0 ? ProjectConstant.ExpireTime.ONE_DAY : expireTime);
            }
            long reVal =  jedisUtils.getSeqNext(lockName,1);
            if (1L == reVal) {
                //获取锁
                result = Boolean.TRUE;
                log.info("获取redis锁:" + lockName + ",成功");
            } else {
                log.info("获取redis锁:" + lockName + ",失败" + reVal);
            }
        } catch (Exception e) {
            log.error("获取redis锁失败:" + lockName, e);
        }
        return result;
    }

    /**
     * 释放锁，直接删除key(直接删除会导致任务重复执行，所以释放锁机制设为超时30s)
     * @param lockName
     * @return
     */
    @Override
    public Boolean releaseLock(String lockName) {
        Boolean result = Boolean.FALSE;
        try {
            jedisUtils.expire(lockName, ProjectConstant.ExpireTime.TEN_SEC);
            log.info("释放redis锁:" + lockName + ",成功");
        } catch (Exception e) {
            log.error("释放redis锁失败:" + lockName, e);
        }
        return result;
    }
}
