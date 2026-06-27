package com.hbb.ai.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学科表
 * </p>
 *
 * @author hbbcn
 * @since 2026-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("course")
@Schema(description = "学科表")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;


    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    @Schema(description = "学科名称")
    private String name;

    @Schema(description = "学历背景要求：0-无，1-初中，2-高中、3-大专、4-本科以上")
    private Integer edu;

    @Schema(description = "课程类型：编程、设计、自媒体、其它")
    private String type;

    @Schema(description = "课程价格")
    private Long price;

    @Schema(description = "学习时长，单位: 天")
    private Integer duration;


}
