package com.atguigu.eduService.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduService.entity.EduCourse;
import com.atguigu.eduService.entity.EduTeacher;
import com.atguigu.eduService.service.EduCourseService;
import com.atguigu.eduService.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther hyx
 */
@RestController
@RequestMapping("/eduService/indexfront")
//@CrossOrigin
public class IndexController {
  @Autowired
    EduCourseService eduCourseService;

  @Autowired
    EduTeacherService eduTeacherService;

    @GetMapping("index")
    public R index(){
//          查询前八名课程
        QueryWrapper<EduCourse> wrapperCourse = new QueryWrapper<>();
        wrapperCourse.orderByDesc("id");
        wrapperCourse.last("limit 8");
        List<EduCourse> courses = eduCourseService.list(wrapperCourse);

//        查询前四名讲师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");

        List<EduTeacher> teachers = eduTeacherService.list(wrapperTeacher);

        return R.ok().data("courses",courses).data("teachers",teachers);
    }
}
