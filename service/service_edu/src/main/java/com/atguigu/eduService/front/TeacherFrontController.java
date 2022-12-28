package com.atguigu.eduService.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduService.entity.EduCourse;
import com.atguigu.eduService.entity.EduTeacher;
import com.atguigu.eduService.service.EduCourseService;
import com.atguigu.eduService.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @auther hyx
 */
@RestController
@RequestMapping("/eduService/teacherfront")
//@CrossOrigin
public class TeacherFrontController {
    @Autowired
    EduTeacherService eduTeacherService;

    @Autowired
    EduCourseService eduCourseService;

    @PostMapping("getTeacherPage/{current}/{limit}")
    public R getTeacherPage(@PathVariable Long current ,@PathVariable Long limit){
        Page<EduTeacher> page = new Page<>(current,limit);

        Map<String, Object> map = eduTeacherService.getFrontTeacherList(page);

        return R.ok().data(map);
    }

    @PostMapping("getTeacherInfo/{tid}")
    public R getTeacherInfo(@PathVariable String tid){
//        查询讲师的基本信息
        EduTeacher teacher = eduTeacherService.getById(tid);


//                查询讲师所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",tid);

        List<EduCourse> courseOfTeacher = eduCourseService.list(wrapper);

        return R.ok().data("teacherInfo",teacher).data("course",courseOfTeacher);
    }
}
