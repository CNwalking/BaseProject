package com.walking.project.controller;

import com.walking.project.common.Result;
import com.walking.project.dataobject.vo.UserVO;
import com.walking.project.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/10 9:47 上午
 * @Description:
 */
@Slf4j
@Api(tags = "UserController", description = "用户模块")
@RestController("UserController")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "测试post方法连接", notes = "测试post方法连接")
    @PostMapping("/post/connection/test")
    public UserVO testPostConnection(@RequestBody @Valid UserVO vo) {
        return vo;
    }

    @ApiOperation(value = "测试get方法连接", notes = "测试get方法连接")
    @GetMapping("/get/connection/test")
    public Result<UserVO> testGetConnection() {
        UserVO vo = new UserVO();
        vo.setAccount("getTestSuccess");
        vo.setEmail("success@success.com");
        return new Result(vo);
    }
}
