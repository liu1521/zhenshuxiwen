package com.book.novel.util;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: shrio工具类
 */
public class ShrioUtil {

    /**
     * 加盐加密
     *
     * @param srcPwd 原始密码
     * @param salt 用户名
     * @return
     */
    public static String encryptPassword(Object srcPwd, Object salt) {
        return new SimpleHash("MD5", srcPwd, salt, 1024).toString();
    }

}
