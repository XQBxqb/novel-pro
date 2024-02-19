    package com.novel.core.entity;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableField;
    import com.baomidou.mybatisplus.annotation.TableId;
    import com.baomidou.mybatisplus.annotation.TableName;
    import com.fasterxml.jackson.annotation.JsonFormat;
    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

    import java.io.Serializable;
    import java.time.LocalDateTime;

    /**
    * <p>
    * 小说信息
    * </p>
    *
    * @author zk
    * @since 2023-09-02
    */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("book_info")
@ApiModel(value="BookInfo对象", description="小说信息")
public class BookInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "作品方向;0-男频 1-女频")
    @TableField("work_direction")
    private Integer workDirection;

    @ApiModelProperty(value = "类别ID")
    @TableField("category_id")
    private Long categoryId;

    @ApiModelProperty(value = "类别名")
    @TableField("category_name")
    private String categoryName;

    @ApiModelProperty(value = "小说封面地址")
    @TableField("pic_url")
    private String picUrl;

    @ApiModelProperty(value = "小说名")
    @TableField("book_name")
    private String bookName;

    @ApiModelProperty(value = "作家id")
    @TableField("author_id")
    private Long authorId;

    @ApiModelProperty(value = "作家名")
    @TableField("author_name")
    private String authorName;

    @ApiModelProperty(value = "书籍描述")
    @TableField("book_desc")
    private String bookDesc;

    @ApiModelProperty(value = "评分;总分:10 ，真实评分 = score/10")
    private Integer score;

    @ApiModelProperty(value = "书籍状态;0-连载中 1-已完结")
    @TableField("book_status")
    private Integer bookStatus;

    @ApiModelProperty(value = "点击量")
    @TableField("visit_count")
    private Long visitCount;

    @ApiModelProperty(value = "总字数")
    @TableField("word_count")
    private Integer wordCount;

    @ApiModelProperty(value = "评论数")
    @TableField("comment_count")
    private Integer commentCount;

    @ApiModelProperty(value = "最新章节ID")
    @TableField("last_chapter_id")
    private Long lastChapterId;

    @ApiModelProperty(value = "最新章节名")
    @TableField("last_chapter_name")
    private String lastChapterName;

    @ApiModelProperty(value = "最新章节更新时间")
    @TableField("last_chapter_update_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastChapterUpdateTime;

    @ApiModelProperty(value = "是否收费;1-收费 0-免费")
    @TableField("is_vip")
    private Integer isVip;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


}
