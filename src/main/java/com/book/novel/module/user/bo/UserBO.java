package com.book.novel.module.user.bo;

import com.book.novel.module.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: liu
 * @Date: 2020/8/8
 * @Description: 用户登陆信息BO
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBO {

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
     * 性别  0未知 1男 2女
     */
    private Integer sex;

    /**
     * 头像base64图片
     */
    private String headImg;

    /**
     * 简介
     */
    private String introduce;

    /**
     * 账号状态  0未激活 1可使用 2已禁用
     */
    private Integer status;

    /**
     * 经验
     */
    private Integer exp;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 推荐票数量
     */
    private Integer recommend;

    /**
     * 角色
     */
    private Integer roleId;

   public UserBO(UserEntity u) {
       this.id = u.getId();
       this.username = u.getUsername();
       this.email = u.getEmail();
       this.sex = u.getSex();
       this.introduce = u.getIntroduce();
       this.status = u.getStatus();
       this.exp = u.getExp()%30;
       this.level = u.getExp()/30+1;
       this.recommend = u.getRecommend();
       this.roleId = u.getRoleId();
   }
}
