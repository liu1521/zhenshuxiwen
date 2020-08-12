package com.book.novel.module.user.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 用户实体类
 */

@Data
public class UserEntity implements Serializable {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 性别  0未知 1男 2女
     */
    private Integer sex;

    /**
     * 头像url  /开头表示图片在磁盘中
     */
    private String headImgUrl;

    /**
     * 简介
     */
    private String introduce;

    /**
     * 账号状态  0未激活 1可使用 2已禁用
     */
    private Integer status;

    /**
     * 登陆次数
     */
    private int loginCount;

    /**
     * 上次登陆ip
     */
    private String lastLoginIp;

    /**
     * 上次登陆时间
     */
    private Date lastLoginTime;

    /**
     * 经验  floor(exp/30)则为当前等级  exp%30为当前经验
     */
    private Integer exp;

    /**
     * 账号创建时间
     */
    private Date createTime;

    /**
     * 推荐票数量
     */
    private Integer recommend;

    /**
     * 角色id
     */
    private Integer roleId;
}
