package com.walking.project.dataobject.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/15 3:35 下午
 * @Description:
 */
@Data
public class LoginUser implements Serializable {
    public Long userId;

    public String account;

    public String name;

    public LoginUser(){

    }

    public LoginUser(String account) {
        this.account = account;
    }

    public LoginUser(Long userId, String account, String name) {
        this.userId = userId;
        this.account = account;
        this.name = name;
    }
}
