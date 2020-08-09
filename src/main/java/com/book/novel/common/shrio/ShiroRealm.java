package com.book.novel.common.shrio;

import com.book.novel.module.role.RoleService;
import com.book.novel.module.role.entity.RoleEntity;
import com.book.novel.module.user.UserService;
import com.book.novel.module.user.entity.UserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description:
 */

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 角色缓存
     */
    @Autowired
    private static final ConcurrentHashMap<String, RoleEntity> roleCache = new ConcurrentHashMap<>();

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        UserEntity userEntity = (UserEntity) subject.getPrincipal();

        RoleEntity roleEntity = roleCache.get(userEntity.getUsername());
        if (roleEntity == null) {
            roleEntity = roleService.getRoleById(userEntity.getRoleId());
        }
        roleCache.put(userEntity.getUsername(), roleEntity);
        authorizationInfo.addRole(roleEntity.getRoleName());
        return authorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        UserEntity userEntity = userService.getUserByUsername(token.getUsername());

        if(userEntity==null){
            return null;
        }

        return new SimpleAuthenticationInfo(userEntity,userEntity.getPassword(),"");
    }

}
