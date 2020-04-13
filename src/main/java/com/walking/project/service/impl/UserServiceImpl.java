package com.walking.project.service.impl;

import com.walking.project.mapper.UserMapper;
import com.walking.project.dataobject.entity.User;
import com.walking.project.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
* @Author: CNwalking
* @DateTime: 2020/04/13
* @Description: TODO
*/
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

}
