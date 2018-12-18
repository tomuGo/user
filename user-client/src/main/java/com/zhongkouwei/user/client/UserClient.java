package com.zhongkouwei.user.client;

import com.zhongkouwei.user.common.AppConstants;
import com.zhongkouwei.user.common.model.PasswordModel;
import com.zhongkouwei.user.common.model.ResultSub;
import com.zhongkouwei.user.common.model.UserInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = AppConstants.APP)
public interface UserClient {

    @RequestMapping(value = AppConstants.USER_URL + "/user/{userId}", method = RequestMethod.GET)
    ResultSub<UserInfo> getUser(@PathVariable(value = "userId") Integer userId);

    @RequestMapping(value = AppConstants.USER_URL + "login", method = RequestMethod.GET)
    ResultSub<UserInfo> login(@RequestParam("account") String account, @RequestParam("password") String password);

    @RequestMapping(value = AppConstants.USER_URL + "user", method = RequestMethod.POST)
    ResultSub<Integer> addUser(@RequestBody UserInfo user);

    @RequestMapping(value = AppConstants.USER_URL + "/user/{id}", method = RequestMethod.PUT)
    ResultSub<Boolean> updateUser(@RequestBody UserInfo user, @PathVariable("id") Integer userId);

    @RequestMapping(value = AppConstants.USER_URL + "user/updatePassword", method = RequestMethod.PUT)
    ResultSub<Boolean> updatePassword(@RequestBody PasswordModel passwordModel);
}
