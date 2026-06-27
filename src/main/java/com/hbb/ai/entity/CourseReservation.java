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
 * 
 * </p>
 *
 * @author hbbcn
 * @since 2026-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("course_reservation")
@Schema(description = "")
public class CourseReservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "预约课程")
    private String course;
    @Schema(description = "学生姓名")
    private String studentName;

    @Schema(description = "联系方式")
    private String contactInfo;

    @Schema(description = "预约校区")
    private String school;

    @Schema(description = "备注")
    private String remark;


}
