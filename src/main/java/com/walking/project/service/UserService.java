package com.walking.project.service;
import com.walking.project.common.Result;
import com.walking.project.dataobject.entity.User;
import com.walking.project.dataobject.vo.UserVO;

import javax.servlet.http.HttpServletResponse;

/**
* @Author: CNwalking
* @DateTime: 2020/04/15
* @Description: TODO
*/
public interface UserService {

    Result isLoginSuccess(UserVO vo, HttpServletResponse response);

}
