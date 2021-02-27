package com.book.novel.module.user.dto;

import lombok.Data;

/**
 * @Author: liu
 * @Date: 2021/2/27
 * @Description:
 */

@Data
public class AddressDTO {

    private Integer addressId;

    private String introduce;

    private String address;

    private String receiver;

    private Integer phone;

    private Boolean isDefault;

}
