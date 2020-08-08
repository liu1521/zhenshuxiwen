package com.book.novel.module.role.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 角色实体类
 */

@Data
public class RoleEntity implements Serializable {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 角色名
     */
    private String roleName;
}
