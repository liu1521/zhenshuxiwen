package com.book.novel.module.user;

import com.book.novel.module.user.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Author: liu
 * @Date: 2020/8/7
 */

@Mapper
@Component
public interface UserMapper {

    UserEntity getUserById(@Param("id") Integer id);

    UserEntity getUserByUsername(@Param("username") String username);

    void updateUserLoginInfo(UserEntity userEntity);

    UserEntity getUserByUsernameAndPassword(@Param("username") String loginName, @Param("password") String loginPwd);

    Integer getIdByUsername(@Param("username") String username);

    Integer getIdByEmail(@Param("email") String email);

    UserEntity getUserByEmail(@Param("email") String email);

    void updateStatusTo2ById(@Param("id") Integer id);

    void saveUser(UserEntity saveUser);

    String getEmailByUsername(@Param("username") String loginName);

    void updateUserInfo(UserEntity userEntity);
}
