package com.zhongkouwei.user.common.model;
import com.zhongkouwei.user.common.enums.AccountType;
import com.zhongkouwei.user.common.group.Group;
import lombok.Data;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name="user")
@Entity
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1076334231083382764L;

    @NotNull(message = "用户UserId不能为空", groups = {Group.updateUser.class})
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private Integer userId;

    @Pattern(regexp = "^[\\w\\u4e00-\\u9fa5]+$", message = "用户名格式不正确，只能由数字/字母/汉字/下划线组成", groups = {
            Group.insertUser.class, Group.updateUser.class
    })
    private String username;

    @Pattern(regexp = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$",
            message = "邮箱格式不正确", groups = {Group.insertUser.class,Group.updateUser.class
    })
    private String email;

    @Pattern(regexp = "\\d{11}", message = "手机号格式不正确", groups = {
    })
    private String phone;

    private String password;

    private Byte sex;

    private Byte isManage;

    private Byte status;

    private Date createdTime;

    private String picUrl;

    private Date lastLoginTime;

    private Integer loginTimes;

    private String introduction;

    public void setAccount(String account){

        if(account.matches(AccountType.EMAIL.regex)){
            email=account;
            return;
        }
        if(account.matches(AccountType.USER_NAME.regex)){
            username=account;
            return;
        }
        Assert.isTrue(Boolean.FALSE,"请检查登陆账号格式是否正确");
    }

}
