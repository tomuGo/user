package com.zhongkouwei.user.common.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PasswordModel {

    @NotNull(message = "用户不能为空")
    private Integer userId;

    @NotNull(message = "原密码不能为空")
    private String oldPassword;

    @NotNull(message = "新密码不能为空")
    private String newPassword;
}
