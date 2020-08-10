package com.book.novel.module.user;

import com.book.novel.common.constant.ResponseCodeConst;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.login.dto.LoginDetailDTO;
import com.book.novel.module.user.bo.UserBO;
import com.book.novel.module.user.constant.UserResponseCodeConst;
import com.book.novel.module.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 用户相关业务
 */

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ValueOperations<String, String> redisValueOperations;

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

    public UserEntity getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    public ResponseDTO<ResponseCodeConst> active(String mailUuid) {
        String activeMail = redisValueOperations.get(mailUuid);
        if (StringUtils.isEmpty(activeMail)) {
            return ResponseDTO.wrap(UserResponseCodeConst.ACTIVE_CODE_INVALID);
        }

        UserEntity userEntity = userMapper.getUserByEmail(activeMail);
        if (userEntity == null) {
            return ResponseDTO.wrap(UserResponseCodeConst.ACTIVE_CODE_INVALID);
        }

        userMapper.updateStatusToOneById(userEntity.getId());

        return ResponseDTO.succ();
    }

    public void saveUser(UserEntity saveUser) {
        userMapper.saveUser(saveUser);
    }
}
