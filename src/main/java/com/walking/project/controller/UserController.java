package com.walking.project.controller;

import com.alibaba.fastjson.JSON;
import com.walking.project.common.Result;
import com.walking.project.common.ResultCode;
import com.walking.project.common.annotation.CurrentUser;
import com.walking.project.dataobject.entity.User;
import com.walking.project.dataobject.vo.UserVO;
import com.walking.project.service.UserService;
import com.walking.project.utils.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;

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
        // 可以直接return, ResponseControllerAdvice 会帮你自动拼接出result
        // return vo;
    }

    @PostMapping("/login")
    public Result login(@RequestBody @Valid UserVO vo, HttpServletResponse response) {
        log.info("User {} Login", vo.getAccount());
        return userService.isLoginSuccess(vo, response);
    }

    @GetMapping("/article")
    public Result article() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            log.info("authenticate success");
            return new Result(ResultCode.SUCCESS, "You are already logged in");
        } else {
            return new Result(ResultCode.SUCCESS, "You are guest");
        }
    }

    @GetMapping("/currentUser")
    @RequiresAuthentication
    public UserVO getCurrentUser(@CurrentUser User user) {
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);
        log.info("currentUserVO " + JSON.toJSONString(vo));
        return vo;
    }


    @GetMapping("/requireAuth")
    @RequiresAuthentication
    public Result requireAuth() {
        return new Result(ResultCode.SUCCESS, "You are authenticated");
    }

    @GetMapping("/requireRole")
    @RequiresRoles("admin")
    public Result requireRole() {
        return new Result(ResultCode.SUCCESS,"You are admin");
    }

    @GetMapping("/requirePermission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public Result requirePermission() {
        return new Result(ResultCode.SUCCESS, "You are visiting permission require edit,view");
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result unauthorized() {
        return new Result(ResultCode.FAILED, "Unauthorized");
    }


}
