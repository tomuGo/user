package com.zhongkouwei.user.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
* @Author wangyiming1031@aliyun.com
* @Description 登陆账号类型
* @Date 2018/9/27 17:58
**/
@AllArgsConstructor
public enum AccountType {

    PHONE("\\d{11}"),

    USER_NAME("^[\\w\\u4e00-\\u9fa5]+$"),

    EMAIL("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$"),;

    public String regex;

    public static void main(String[] args) {
        for(AccountType accountType:AccountType.values()){
            System.out.println(accountType);
        }
    }

}
