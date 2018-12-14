package com.zhongkouwei.user.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
* @Author wangyiming1031@aliyun.com
* @Description 第三方登陆表
* @Date 2018/10/17 13:57
**/
@Data
@NoArgsConstructor
@Table(name="thirdpart_user")
@Entity
public class ThirdpartUser implements Serializable {

    private static final long serialVersionUID = 5921863531635800110L;
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private Integer id;

    private Integer userId;

    private Byte thirdpartType;

    private String thirdpartId;

    private String accessToken;

    private Long accessTokenExpireTime;

    private Date createTime;
}
