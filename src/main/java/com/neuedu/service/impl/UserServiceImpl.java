package com.neuedu.service.impl;

import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.UserInfoMapper;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 杜晓鹏
 * @create 2019-01-04 19:41
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public ServerResponse login(String username, String password) {
        //非空判断
        if (username == null || username.equals(""))
            return ServerResponse.serverResponseError("用户名不能为空");
        if (password == null || password.equals(""))
            return ServerResponse.serverResponseError("密码不能为空");
        //查看当前用户存不存在
        int result = userInfoMapper.checkUsername(username);
        if (result == 0)
            return ServerResponse.serverResponseError("用户民不存在");
        //根据用户名和密码找到当前用户
        UserInfo userInfo = userInfoMapper.selectUserByUsernameAndPassword(username, password);
        if (userInfo == null)
            return ServerResponse.serverResponseError("密码错误");
        //返回结果
        return ServerResponse.serverResponseSuccess(userInfo);
    }

    @Override
    public ServerResponse register(UserInfo userInfo) {
        //非空验证
        if (userInfo == null)
            return ServerResponse.serverResponseError("请填写用户信息");
        //检查用户名是否存在
        if (userInfoMapper.checkUsername(userInfo.getUsername()) > 0)
            return ServerResponse.serverResponseError("用户名已存在");
        //检查邮箱是否存在
        if (userInfoMapper.checkEmail(userInfo.getEmail()) > 0 )
            return ServerResponse.serverResponseError("邮箱已存在");
        //给用户设置角色
        userInfo.setRole(Const.RoleEnum.ROLE_CUSTOMER.getCode());
        //添加用户
        int result = userInfoMapper.insert(userInfo);
        if (result > 0)
            return ServerResponse.serverResponseSuccess("注册成功");
        return ServerResponse.serverResponseError("注册失败");
    }
}
