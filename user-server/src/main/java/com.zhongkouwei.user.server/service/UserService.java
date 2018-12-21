package com.zhongkouwei.user.server.service;

import com.zhongkouwei.user.common.AppConstants;
import com.zhongkouwei.user.common.enums.UserStatus;
import com.zhongkouwei.user.common.group.Group;
import com.zhongkouwei.user.common.model.PasswordModel;
import com.zhongkouwei.user.common.model.UserInfo;
import com.zhongkouwei.user.server.component.RedisComponent;
import com.zhongkouwei.user.server.component.SecurityComponent;
import com.zhongkouwei.user.server.reporitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    ValidatorService validatorService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RedisComponent redisComponent;

    private static java.util.concurrent.BlockingQueue BlockingQueue=new ArrayBlockingQueue(10);

    private static ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(2,2,1,TimeUnit.MINUTES,BlockingQueue);


    public void login(UserInfo loginUser){
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Integer loginTimes=loginUser.getLoginTimes();
                if(loginTimes==null){
                    loginUser.setLoginTimes(1);
                }else {
                    loginUser.setLoginTimes(++loginTimes);
                }
                loginUser.setLastLoginTime(new Date());
                userRepository.save(loginUser);
            }
        });
    }

    public Integer addUser(UserInfo user) {
        validatorService.validate(user, Group.insertUser.class);
        Assert.notNull(user.getPassword(),"密码在哪");
        Assert.isTrue(!(user.getEmail() == null && user.getUsername() == null), "邮箱和用户名都为空");
        if (!StringUtils.isEmpty(user.getEmail())) {
            List<UserInfo> userEmail = userRepository.findByEmail(user.getEmail());
            Assert.isTrue(CollectionUtils.isEmpty(userEmail), "邮箱已存在");
        }
        if(!StringUtils.isEmpty(user.getUsername())){
            List<UserInfo> userName = userRepository.findByUsername(user.getUsername());
            Assert.isTrue(CollectionUtils.isEmpty(userName), "用户名已存在");
        }
        String password=SecurityComponent.encryptUserPassword(user.getPassword());
        user.setPassword(password);
        user.setLoginTimes(0);
        user.setStatus(UserStatus.NORNAL.status);
        user.setCreatedTime(new Date());
        user.setPicUrl("1");
        UserInfo newUser = userRepository.save(user);
        redisComponent.set(AppConstants.USERINFO+newUser.getUserId(),newUser);
        return newUser.getUserId();
    }

    public void updateUser(UserInfo user) {
        validatorService.validate(user, Group.updateUser.class);
        if (!StringUtils.isEmpty(user.getEmail())) {
            List<UserInfo> userEmail = userRepository.findByEmail(user.getEmail());
            Assert.isTrue(userEmail.size() == 0 || (userEmail.size() == 1 && userEmail.get(0).getUserId().equals(user.getUserId())), "邮箱已存在");
        }
        if(!StringUtils.isEmpty(user.getUsername())){
            List<UserInfo> userName = userRepository.findByUsername(user.getUsername());
            Boolean boo= CollectionUtils.isEmpty(userName) || (userName.size() == 1 && userName.get(0).getUserId().equals(user.getUserId()));
            Assert.isTrue(boo, "用户名已存在");
        }
        UserInfo userInfo=converUpdateUser(user.getUserId(),user);
        UserInfo updateUser=userRepository.save(userInfo);
        redisComponent.set(AppConstants.USERINFO+updateUser.getUserId(),updateUser);
    }

    public void updatePassword(PasswordModel passwordModel){
        validatorService.validate(passwordModel);
        UserInfo userInfo=userRepository.findOne(passwordModel.getUserId());
        Assert.notNull(userInfo,"用户不存在");
        String encOldPassword=SecurityComponent.encryptUserPassword(passwordModel.getOldPassword());
        Assert.isTrue(userInfo.getPassword().equals(encOldPassword),"原密码不正确");
        String password=SecurityComponent.encryptUserPassword(passwordModel.getNewPassword());
        userRepository.updatePassword(passwordModel.getUserId(),password);
    }

    private UserInfo converUpdateUser(Integer userId, UserInfo updateUser){
        UserInfo userInfo=userRepository.findOne(userId);
        if(!StringUtils.isEmpty(updateUser.getIsManage())){
            userInfo.setIsManage(updateUser.getIsManage());
        }
        if(!StringUtils.isEmpty(updateUser.getPhone())){
            userInfo.setPhone(updateUser.getPhone());
        }
        if(!StringUtils.isEmpty(updateUser.getEmail())){
            userInfo.setEmail(updateUser.getEmail());
        }
        if(!StringUtils.isEmpty(updateUser.getStatus())){
            userInfo.setStatus(updateUser.getStatus());
        }
        if(!StringUtils.isEmpty(updateUser.getUsername())){
            userInfo.setUsername(updateUser.getUsername());
        }
        if(!StringUtils.isEmpty(updateUser.getIntroduction())){
            userInfo.setIntroduction(updateUser.getIntroduction());
        }
        if(!StringUtils.isEmpty(updateUser.getPicUrl())){
            userInfo.setPicUrl(updateUser.getPicUrl());
        }
        if(!StringUtils.isEmpty(updateUser.getSex())){
            userInfo.setSex(updateUser.getSex());
        }
        return userInfo;
    }


}
