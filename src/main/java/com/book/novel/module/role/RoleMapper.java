package com.book.novel.module.role;

import com.book.novel.module.role.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Author: liu
 * @Date: 2020/8/7
 */

@Mapper
@Component
public interface RoleMapper {
    RoleEntity getRoleById(@Param("id") Integer id);
}
