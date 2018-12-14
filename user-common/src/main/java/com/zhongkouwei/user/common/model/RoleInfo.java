package com.zhongkouwei.user.common.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "role")
@Entity
public class RoleInfo implements Serializable {

    private static final long serialVersionUID = -8007968219168855916L;

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private Integer roleId;

    private String roleName;

    private String description;

    public RoleInfo(String roleName,String description){
        this.roleId=-1;
        this.roleName=roleName;
        this.description=description;
    }
}
