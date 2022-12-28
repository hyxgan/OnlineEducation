package com.atguigu.eduService.service;

import com.atguigu.eduService.entity.EduCourse;
import com.atguigu.eduService.entity.frontVo.FrontCourseQueryVo;
import com.atguigu.eduService.entity.vo.CourseInfoForm;
import com.atguigu.eduService.entity.vo.CoursePublishVo;
import com.atguigu.eduService.entity.frontVo.CourseWebVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-25
 */
public interface EduCourseService extends IService<EduCourse> {

    String InsertCourse(CourseInfoForm courseInfoForm);

    void updateCourse(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseInfo(String courseId);


    CoursePublishVo getCoursePublishInfo(String courseId);

    Map<String, Object> getCoursePageByCondition(Page<EduCourse> coursePage, FrontCourseQueryVo frontCourseQueryVo);

    CourseWebVo getBaseCourseInfo(String cid);
}
