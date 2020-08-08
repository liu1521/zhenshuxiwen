package com.book.novel.module.login.dto;

import lombok.Data;

/**
 * @Author: liu
 * @Date: 2020/8/8
 * @Description: 验证码DTO
 */

@Data
public class KaptchaDTO {

    /**
     * 验证码uuid
     */
    private String uuid;

    /**
     * 验证码
     */
    private String code;

}
