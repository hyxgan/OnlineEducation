package com.atguigu.eduService.front;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.EduCourseOrder;
import com.atguigu.eduService.client.OrderClient;
import com.atguigu.eduService.entity.EduCourse;
import com.atguigu.eduService.entity.chapter.ChapterVo;
import com.atguigu.eduService.entity.frontVo.FrontCourseQueryVo;
import com.atguigu.eduService.entity.frontVo.CourseWebVo;
import com.atguigu.eduService.service.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @auther hyx
 */
@RestController
@RequestMapping("/eduService/coursefront")
//@CrossOrigin
public class CourseFrontController {
    @Autowired
    EduCourseService eduCourseService;

    @Autowired
    EduSubjectService eduSubjectService;

    @Autowired
    EduTeacherService eduTeacherService;

    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    EduChapterService eduChapterService;

    @Autowired
    OrderClient orderClient;

//    分页条件查询
    @PostMapping("{page}/{limit}")
    public R getCoursePageList(@PathVariable Long page, @PathVariable Long limit,
                               @RequestBody FrontCourseQueryVo frontCourseQueryVo
                               ){
        Page<EduCourse> coursePage = new Page<>(page, limit);

        Map<String,Object> map = eduCourseService.getCoursePageByCondition(coursePage,frontCourseQueryVo);

        return R.ok().data(map);
    }

//    查询课程的详细信息
    /*
    * 1.课程的基本信息。
    * 2.课程分类
    * 3.课程的讲师
    * 4.课程描述信息
    * 5.课程的章节和小节
    * */

    @GetMapping("getCourseInfo/{cid}")
    public R getCourseInfo(@PathVariable("cid") String cid, HttpServletRequest request){
        //    查询课程的基本信息
        CourseWebVo baseCourseInfo = eduCourseService.getBaseCourseInfo(cid);


//        查询课程的章节和小节信息
        List<ChapterVo> chapterVideo = eduChapterService.getChapterVideoByCourseId(cid);

//        查询课程是否支付
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
//
        Boolean isBuy = orderClient.isBuyCourse(cid, memberId);
        return R.ok().data("baseCourseInfo",baseCourseInfo)
                .data("chapterVideo",chapterVideo)
                .data("isBuy",isBuy);
    }

    //        远程调用，根据课程ID获取课程信息
    //    通过课程ID获取课程信息,调用端接受课程信息和被调用端返回课程信息必须是同一个类，不然接受不了
    //    所以把类放到common_util模块内。
    @PostMapping("getCourseInfoOrder/{courseId}")
    public EduCourseOrder getCourseInfoOrder(@PathVariable("courseId") String courseId){
        CourseWebVo baseCourseInfo = eduCourseService.getBaseCourseInfo(courseId);
        EduCourseOrder eduCourseOrder = new EduCourseOrder();
        BeanUtils.copyProperties(baseCourseInfo,eduCourseOrder);
        return eduCourseOrder;
    }
}
