package com.atguigu.eduService.mapper;

import com.atguigu.eduService.entity.EduCourse;
import com.atguigu.eduService.entity.vo.CoursePublishVo;
import com.atguigu.eduService.entity.frontVo.CourseWebVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-11-25
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
        CoursePublishVo selectCoursePublishById(@Param("courseId") String courseId);

        CourseWebVo selectInfoWebById(String courseId);
}
