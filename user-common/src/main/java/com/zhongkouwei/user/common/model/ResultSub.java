package com.zhongkouwei.user.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultSub<T> {

    private Integer status;

    private T data;

    private String message;

    public ResultSub(T data){
        this.data=data;
        status=200;
    }

}
