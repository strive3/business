package com.neuedu.controller.portal;

import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author 杜晓鹏
 * @create 2019-01-04 18:50
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/login.do")
    public ServerResponse login(HttpSession session, String username, String password) {

        ServerResponse serverResponse = userService.login(username, password);

        if (serverResponse.isSuccess()) {//登陆成功
            UserInfo userInfo = (UserInfo) serverResponse.getData();
            session.setAttribute(Const.CURRENTUSER,userInfo);
        }
        return serverResponse;
    }

    @RequestMapping("/register.do")
    public ServerResponse register(HttpSession session, UserInfo userInfo) {
        //调用service中的添加方法
        ServerResponse serverResponse = userService.register(userInfo);

        return serverResponse;
    }
}
