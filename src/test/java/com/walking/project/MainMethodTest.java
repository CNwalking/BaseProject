package com.walking.project;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.walking.project.dataobject.dto.UserDTO;
import com.walking.project.dataobject.entity.User;
import com.walking.project.service.impl.convert.UserConverter;

/**
 * @Author: CNwalking
 * @DateTime: 2020/8/15 2:33 下午
 * @Description: main方法测试简单代码
 */
public class MainMethodTest {
    public static void main(String[] args) {
        User user = new User();
        user.setUserId(1L);
        user.setAccount("sss");
        user.setName("sss");
        user.setPassword("sss");
        user.setEmail("s@qq.com");
        user.setRoleId(1);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setStatus("NORMAL");
        UserDTO userDTO = UserConverter.INSTANCE.do2dto(user);
        System.out.println(JSON.toJSONString(userDTO));
    }

}
