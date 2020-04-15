package com.walking.project.service;
import com.walking.project.dataobject.entity.User;

/**
* @Author: CNwalking
* @DateTime: 2020/04/15
* @Description: TODO
*/
public interface UserService {

    User getUserByAccount(String account);

}
