package com.zhongkouwei.user.server.component;

import com.alibaba.fastjson.JSONObject;
import com.zhongkouwei.user.common.AppConstants;
import com.zhongkouwei.user.common.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisComponent {

    @Autowired
    RedisTemplate redisTemplate;

    public void set(String key,Object value){
        redisTemplate.opsForValue().set(key,value);
    }

    public void del(List<String> keys){
        redisTemplate.delete(keys);
    }

    public Boolean exits(String key){
        return redisTemplate.hasKey(key);
    }

    public UserInfo getUserInfoFromRedisByUserId(Integer userId){
        Object obj=redisTemplate.opsForValue().get(AppConstants.USERINFO+userId);
        UserInfo userInfo=null;
        if(obj!=null){
            try {
                userInfo=JSONObject.parseObject(JSONObject.toJSONString(obj),UserInfo.class);
            }catch (Exception e){
                System.out.println(e);
                return null;
            }
        }
        return userInfo;
    }

}
