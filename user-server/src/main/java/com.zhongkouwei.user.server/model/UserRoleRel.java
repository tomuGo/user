package com.zhongkouwei.user.server.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "user_role_rel")
public class UserRoleRel implements Serializable {

    private static final long serialVersionUID = 654068944471328186L;
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private Integer id;

    private Integer roleId;

    private Integer userId;

    private Date createTime;


}
