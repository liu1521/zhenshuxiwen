package com.book.novel.module.login.bo;

import com.book.novel.module.user.bo.UserBO;
import lombok.Data;

/**
 * @Author: liu
 * @Date: 2020/8/8
 * @Description: 请求tokenBO
 */

@Data
public class RequestTokenBO {

    private Integer requestUserId;

    private UserBO userBO;

    public RequestTokenBO(UserBO userBO) {
        this.userBO = userBO;
        this.requestUserId = userBO.getId();
    }
}
