package com.zhongkouwei.user.server.controller;

import com.zhongkouwei.user.common.AppConstants;
import com.zhongkouwei.user.common.model.ResultSub;
import com.zhongkouwei.user.common.model.RoleInfo;
import com.zhongkouwei.user.server.reporitory.EntityManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = AppConstants.APP_PATH)
public class RoleController {

    @Autowired
    EntityManagerMapper entityManagerMapper;

    @RequestMapping(value = "roles/user/{id}",method = RequestMethod.GET)
    public ResultSub<List<RoleInfo>> getRoleInfosByUserId(@PathVariable("id")Integer userId){
        List<RoleInfo>roleInfos=entityManagerMapper.findRolesByUserId(userId);
        return new ResultSub<>(roleInfos);
    }

}
