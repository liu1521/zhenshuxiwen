package com.book.novel.module.novel.dto;

import com.book.novel.common.constant.ExamineStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: liu
 * @Date: 2020/9/10
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NovelExamineDTO {

    private Integer id;

    private String title;

    private String categoryName;

    private String author;

    private String pic;

    private String introduce;

    private String status;

    public NovelExamineDTO(NovelDetailDTO novelDetailDTO) {
        this.id = novelDetailDTO.getId();
        this.title = novelDetailDTO.getTitle();
        this.categoryName = novelDetailDTO.getCategoryName();
        this.author = novelDetailDTO.getAuthor();
        this.pic = novelDetailDTO.getPic();
        this.introduce = novelDetailDTO.getIntroduce();

        if (ExamineStatusEnum.EXAMINE_WAIT.getValue().equals(novelDetailDTO.getStatus())) {
            this.status = ExamineStatusEnum.EXAMINE_WAIT.getDesc();
        } else if (ExamineStatusEnum.EXAMINE_SUCCESS.getValue().equals(novelDetailDTO.getStatus())) {
            this.status = ExamineStatusEnum.EXAMINE_SUCCESS.getDesc();
        } else if (ExamineStatusEnum.EXAMINE_FAIL.getValue().equals(novelDetailDTO.getStatus())) {
            this.status = ExamineStatusEnum.EXAMINE_FAIL.getDesc();
        }
    }
}
