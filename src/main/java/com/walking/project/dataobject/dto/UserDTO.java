package com.walking.project.dataobject.dto;

import java.util.Date;

/**
 * @Author: CNwalking
 * @DateTime: 2020/8/15 2:09 下午
 * @Description:
 */
public class UserDTO {

    private Long userId;

    private String account;

    private String nameForTest;

    private String password;

    private String email;

    private Integer roleId;

    private Date createTime;

    private Date updateTime;

    private String status;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNameForTest() {
        return nameForTest;
    }

    public void setNameForTest(String nameForTest) {
        this.nameForTest = nameForTest;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
