package com.walking.project.dataobject.vo;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/10 9:52 上午
 * @Description:
 */
public class UserVO {
    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不能为空")
    private Long id;

    @ApiModelProperty("用户名")
    @NotNull(message = "用户名不能为空")
    @Size(min = 3,max = 16,message = "用户名长度需要3-16个字符")
    private String account;

    @ApiModelProperty("密码")
    @NotNull(message = "密码不能为空")
    @Size(min = 6,max = 16,message = "密码长度需要6-16个字符")
    private String password;

    @ApiModelProperty("email")
    @NotNull(message = "邮箱不能为空")
    @Email(message = "邮箱格式不对")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
}
