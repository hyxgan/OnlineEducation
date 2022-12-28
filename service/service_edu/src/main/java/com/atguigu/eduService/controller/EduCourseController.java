package com.atguigu.eduService.controller;


import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.EduCourseOrder;
import com.atguigu.eduService.entity.EduCourse;
import com.atguigu.eduService.entity.EduCourseDescription;
import com.atguigu.eduService.entity.vo.CourseInfoForm;
import com.atguigu.eduService.entity.vo.CoursePublishVo;
import com.atguigu.eduService.entity.vo.CourseQuery;
import com.atguigu.eduService.service.EduChapterService;
import com.atguigu.eduService.service.EduCourseDescriptionService;
import com.atguigu.eduService.service.EduCourseService;
import com.atguigu.eduService.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-25
 */
@RestController
@RequestMapping("/eduService/edu-course")
//@CrossOrigin
public class EduCourseController {
    @Autowired
    EduCourseService eduCourseService;

    @Autowired
    EduVideoService eduVideoService;

    @Autowired
    EduChapterService eduChapterService;

    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;

    @PostMapping("addCourse")
    public R addCourse(@RequestBody CourseInfoForm courseInfoForm)
    {
        String id = eduCourseService.InsertCourse(courseInfoForm);
         return R.ok().data("courseId",id);
    }

//    获取课程信息
    @GetMapping("getCourse/{courseId}")
    public R getCourse(@PathVariable("courseId") String courseId){
         CourseInfoForm courseInfo = eduCourseService.getCourseInfo(courseId);
         return R.ok().data("CourseInfo",courseInfo);
    }

//    修改课程
    @PutMapping("updateCourse")
    public R updateCourse(@RequestBody CourseInfoForm courseInfoForm){
           eduCourseService.updateCourse(courseInfoForm);
           return R.ok();
    }

//    获取提交时的数据
    @GetMapping("getCoursePublishInfo/{courseId}")
    public R getCoursePublishInfoById(@PathVariable("courseId") String courseId){
        CoursePublishVo coursePublishInfo = eduCourseService.getCoursePublishInfo(courseId);
        return R.ok().data("coursePublishInfo",coursePublishInfo);
    }

//    课程发布，将status置为normal
    @PostMapping("/publishCourse/{courseid}")
    public R publishCourseById(@PathVariable("courseid") String courseId){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

//    查询所有课程信息
    @GetMapping("getAllCourse")
    public R getAllCourse(){

        List<EduCourse> list = eduCourseService.list(null);

        return R.ok().data("list",list);

    }
    //    查询所有课程信息
    @GetMapping("getAllCourse/{current}/{limit}")
    public R getAllCourse(@PathVariable("current") Long current ,
                          @PathVariable("limit") Long limit,
                          @RequestBody(required = false) CourseQuery courseQuery){
        String status = null;
        String title = null;
        if (courseQuery != null){
            status = courseQuery.getStatus();
            title = courseQuery.getTitle();

        }

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(status)){
            wrapper.like("status",status);
        }
        if (!StringUtils.isEmpty(title)){
            wrapper.like("level",title);
        }

        Page<EduCourse> page = new Page<EduCourse>(current,limit);

        eduCourseService.page(page, wrapper);
        List<EduCourse> rows = page.getRecords();
        return R.ok().data("rows",rows);

    }

    @DeleteMapping("/deleteCourse/{courseId}")
    public R DeleteCourseById(@PathVariable String courseId){
//            根据课程ID删除小节
          eduVideoService.removeVideoByCourseId(courseId);
//            根据课程ID删除章节
            eduChapterService.removeChapterByCourseId(courseId);
//            根据课程ID删除描述
            eduCourseDescriptionService.removeById(courseId);
//            根据课程ID删除课程
        boolean b = eduCourseService.removeById(courseId);
         if (b){
              return R.ok();
         }
         else{
             return R.error();
         }

    }

}

