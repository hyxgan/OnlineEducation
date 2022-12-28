package com.atguigu.eduService.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @auther hyx
 */
@Data
public class CourseQuery {
    @ApiModelProperty(value = "课程名称")
    private String title;
    @ApiModelProperty(value = "课程状态")
    private String status;
}
