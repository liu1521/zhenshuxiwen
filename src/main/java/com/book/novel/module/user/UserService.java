package com.book.novel.module.user;

import com.book.novel.common.constant.ResponseCodeConst;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.login.dto.LoginDetailDTO;
import com.book.novel.module.user.bo.UserBO;
import com.book.novel.module.user.constant.UserResponseCodeConst;
import com.book.novel.module.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 用户相关业务
 */

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public UserBO getUserBOById(Integer id) {
        UserEntity userEntity = userMapper.getUserById(id);
        UserBO userBO = new UserBO(userEntity);
        return userBO;
    }

    public UserEntity getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    public void updateUserLoginInfo(UserEntity userEntity) {
        userMapper.updateUserLoginInfo(userEntity);
    }

    public UserEntity getUserByUsernameAndPassword(String loginName, String loginPwd) {
        return userMapper.getUserByUsernameAndPassword(loginName, loginPwd);
    }

    public ResponseDTO<ResponseCodeConst> testUsername(String username) {
        if (getIdByUsername(username) != null) {
            return ResponseDTO.wrap(UserResponseCodeConst.LOGIN_NAME_EXISTS);
        } else return ResponseDTO.succ();
    }

    public Integer getIdByUsername(String username) {
        return userMapper.getIdByUsername(username);
    }

    public Integer getIdByEmail(String email) {
        return userMapper.getIdByEmail(email);
    }


}
