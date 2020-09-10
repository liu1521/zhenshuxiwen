package com.book.novel.module.novel.dto;

import com.book.novel.module.novel.constant.NovelStatusEnum;
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

        if (NovelStatusEnum.EXAMINE_WAIT.getValue().equals(novelDetailDTO.getStatus())) {
            this.status = NovelStatusEnum.EXAMINE_WAIT.getDesc();
        } else if (NovelStatusEnum.EXAMINE_SUCCESS.getValue().equals(novelDetailDTO.getStatus())) {
            this.status = NovelStatusEnum.EXAMINE_SUCCESS.getDesc();
        } else if (NovelStatusEnum.EXAMINE_FAIL.getValue().equals(novelDetailDTO.getStatus())) {
            this.status = NovelStatusEnum.EXAMINE_FAIL.getDesc();
        }
    }
}
