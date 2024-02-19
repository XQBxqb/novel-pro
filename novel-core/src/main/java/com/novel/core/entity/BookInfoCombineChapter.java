package com.novel.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 昴星
 * @date 2023-10-03 15:10
 * @explain
 */
@AllArgsConstructor
@Data
public class BookInfoCombineChapter {
    @TableId
    private Long id;

    @TableField("book_id")
    private Long bookId;

    @TableField("chapter_num")
    private Integer chapterNum;

    @TableField("chapter_name")
    private String chapterName;

    @TableField("word_count")
    private Integer wordCount;

    @TableField("last_chapter_update_time")
    private LocalDateTime lastChapterUpdateTime;

    @TableField("is_vip")
    private Integer isVip;
}
