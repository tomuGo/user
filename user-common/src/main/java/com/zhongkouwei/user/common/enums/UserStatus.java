package com.zhongkouwei.user.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public enum UserStatus {

    NORNAL((byte)1),
    DELETE((byte)-1);
    public byte status;

}
