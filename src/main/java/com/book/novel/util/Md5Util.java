package com.book.novel.util;

import org.springframework.util.DigestUtils;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: md5工具类
 */
public class Md5Util {

    /**
     * 加盐加密
     *
     * @param srcPwd 原始密码
     * @param salt 用户名
     * @return
     */
    public static String encryptPassword(Object srcPwd, Object salt) {
        String base = srcPwd + "/" + salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

}
