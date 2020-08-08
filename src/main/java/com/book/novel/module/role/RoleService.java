package com.book.novel.module.role;

import com.book.novel.module.role.entity.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: liu
 * @Date: 2020/8/8
 * @Description: 角色相关业务
 */

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public RoleEntity getRoleById(Integer id) {
        return roleMapper.getRoleById(id);
    }

}
