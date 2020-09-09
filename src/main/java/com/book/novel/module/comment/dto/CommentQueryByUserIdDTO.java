package com.book.novel.module.comment.dto;

import com.book.novel.common.domain.PageParamDTO;
import lombok.Data;

/**
 * @Author: liu
 * @Date: 2020/9/9
 * @Description:
 */

@Data
public class CommentQueryByUserIdDTO extends PageParamDTO {

    private Integer userId;

}
