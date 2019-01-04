package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;

/**
 * @author 杜晓鹏
 * @create 2019-01-04 19:40
 */
public interface UserService {
    ServerResponse login(String username, String password);

    ServerResponse register(UserInfo userInfo);
}
