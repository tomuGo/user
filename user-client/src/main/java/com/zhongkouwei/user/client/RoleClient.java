package com.zhongkouwei.user.client;

import com.zhongkouwei.user.common.AppConstants;
import com.zhongkouwei.user.common.model.ResultSub;
import com.zhongkouwei.user.common.model.RoleInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = AppConstants.APP)
public interface RoleClient {

    @RequestMapping(value = AppConstants.USER_URL +"roles/user/{id}",method = RequestMethod.GET)
    ResultSub<List<RoleInfo>> getRoleInfosByUserId(@PathVariable("id")Integer userId);
}
