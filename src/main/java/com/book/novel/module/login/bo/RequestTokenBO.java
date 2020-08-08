package com.book.novel.module.login.bo;

import com.book.novel.module.user.bo.UserBO;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author: liu
 * @Date: 2020/8/8
 * @Description: 请求tokenBO
 */

@Getter
@ToString
public class RequestTokenBO {

    private Integer requestUserId;

    private UserBO userBO;

    public RequestTokenBO(UserBO userBO) {
        this.userBO = userBO;
        this.requestUserId = userBO.getId();
    }
}
