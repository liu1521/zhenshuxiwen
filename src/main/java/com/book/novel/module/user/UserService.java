package com.book.novel.module.user;

import com.book.novel.common.constant.RedisKeyConstant;
import com.book.novel.common.constant.ResponseCodeConst;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.user.bo.UserBO;
import com.book.novel.module.user.constant.UserResponseCodeConst;
import com.book.novel.module.user.constant.UserSexEnum;
import com.book.novel.module.user.constant.UserStatusEnum;
import com.book.novel.module.user.entity.UserEntity;
import com.book.novel.module.user.vo.UserRegisterFormVO;
import com.book.novel.util.JsonUtil;
import com.book.novel.util.Md5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

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

    public Integer getIdByEmail(String email) {
        return userMapper.getIdByEmail(email);
    }


    public UserEntity getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    public ResponseDTO<ResponseCodeConst> active(String mailUuid) {
        String activeMail = redisValueOperations.get(RedisKeyConstant.WAIT_ACTIVE_USER_PREFIX+mailUuid);
        if (StringUtils.isEmpty(activeMail)) {
            return ResponseDTO.wrap(UserResponseCodeConst.ACTIVE_CODE_INVALID);
        }

        String json = redisValueOperations.get(RedisKeyConstant.WAIT_ACTIVE_USER_PREFIX + mailUuid);
        if (StringUtils.isEmpty(json)) {
            return ResponseDTO.wrap(UserResponseCodeConst.ACTIVE_CODE_INVALID);
        }
        UserRegisterFormVO userRegisterFormVO = (UserRegisterFormVO) JsonUtil.toObject(json, UserRegisterFormVO.class);
        if (userRegisterFormVO == null) {
            return ResponseDTO.wrap(UserResponseCodeConst.ACTIVE_CODE_INVALID);
        }

        UserEntity saveUser = new UserEntity();
        saveUser.setEmail(userRegisterFormVO.getEmail());
        saveUser.setUsername(userRegisterFormVO.getUsername());
        saveUser.setPassword(Md5Util.encryptPassword(userRegisterFormVO.getPassword(), userRegisterFormVO.getUsername()));
        saveUser.setStatus(UserStatusEnum.NORMAL.getValue());
        saveUser.setCreateTime(new Date());

        // 设置性别
        if (UserSexEnum.UNKNOWN.getDesc().equals(userRegisterFormVO.getSex())) {
            saveUser.setSex(UserSexEnum.UNKNOWN.getValue());
        } else if (UserSexEnum.MALE.getDesc().equals(userRegisterFormVO.getSex())) {
            saveUser.setSex(UserSexEnum.MALE.getValue());
        } else if (UserSexEnum.FEMALE.getDesc().equals(userRegisterFormVO.getSex())) {
            saveUser.setSex(UserSexEnum.FEMALE.getValue());
        }

        userMapper.saveUser(saveUser);

        return ResponseDTO.succ();
    }

}
