package com.zhongkouwei.user.server.controller;

import com.zhongkouwei.user.common.AppConstants;
import com.zhongkouwei.user.common.model.ResultSub;
import com.zhongkouwei.user.common.model.UserInfo;
import com.zhongkouwei.user.server.component.SecurityComponent;
import com.zhongkouwei.user.server.reporitory.EntityManagerMapper;
import com.zhongkouwei.user.server.reporitory.UserRepository;
import com.zhongkouwei.user.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Author wangyiming1031@aliyun.com
 * @Description
 * @Date 2018/9/27 14:06
 **/
@RestController
@Slf4j
@RequestMapping(value = AppConstants.APP_PATH)
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManagerMapper entityManagerMapper;

    @Value("${zhongkouwei.uploadFile}")
    String uploadFile;

    @Value("${zhongkouwei.uploadFileWindow}")
    String uploadFileWindow;

    @RequestMapping(value = "login",method = RequestMethod.GET)
    public ResultSub<UserInfo> login(@RequestParam("account") String account, @RequestParam("password") String password){
        UserInfo userInfo = new UserInfo();
        userInfo.setAccount(account);
        userInfo.setPassword(SecurityComponent.encryptUserPassword(password));
        UserInfo loginUser=entityManagerMapper.getLoginUserInfo(userInfo);
        userService.login(loginUser);
        return new ResultSub<>(loginUser);
    }

    @RequestMapping(value = "user", method = RequestMethod.POST)
    public ResultSub<Integer> addUser(@RequestBody UserInfo user) {
        Integer userId = userService.addUser(user);
        return new ResultSub<>(userId);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResultSub<Boolean> updateUser(@RequestBody UserInfo user, @PathVariable("id") Integer userId) {
        user.setUserId(userId);
        userService.updateUser(user);
        return new ResultSub<>(Boolean.TRUE);
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResultSub<UserInfo> getUser(@PathVariable(value = "userId") Integer userId) {
        UserInfo user = userRepository.findOne(userId);
        return new ResultSub<>(user);
    }

    @RequestMapping(value = "user/{id}/updatePassword", method = RequestMethod.GET)
    public ResultSub<Boolean> updatePassword(@PathVariable("id") Integer userId,
                                             @RequestParam("oldPassword") String olePassword,
                                             @RequestParam("newPassword") String newPassword) {
        userService.updatePassword(userId, newPassword, olePassword);
        return new ResultSub<>(Boolean.TRUE);
    }

    @RequestMapping(value = "user/{id}/uploadPic", consumes = "multipart/*", headers = "content-type=multipart/from-data", method = RequestMethod.POST)
    @Transactional
    public ResultSub<Boolean> uploadPic(@PathVariable("id") Integer userId, @RequestParam("imageUrl") MultipartFile pic) throws IOException {
        String filename = uploadFileWindow + pic.getOriginalFilename();
        InputStream is = pic.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        FileOutputStream fileOut = new FileOutputStream(filename);
        BufferedOutputStream bos = new BufferedOutputStream(fileOut);
        byte[] buf = new byte[4096];
        int length = bis.read(buf);
        //保存文件
        while(length != -1)
        {
            bos.write(buf, 0, length);
            length = bis.read(buf);
        }
        bos.close();
        bis.close();

        userRepository.updatePicUrl(userId, filename);
        return new ResultSub<>(Boolean.TRUE);
    }

    @RequestMapping(value = "user/pic",method = RequestMethod.GET)
    public void getPic(@RequestParam("picUrl")String picUrl, HttpServletResponse resp) throws FileNotFoundException {
        OutputStream os = null;
        File file = new File(picUrl);
        FileInputStream is = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        try {
            resp.setContentType("image/jpeg");
            os = resp.getOutputStream();
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
