package com.atguigu.eduService.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.atguigu.eduService.entity.EduCourse;
import com.atguigu.eduService.entity.EduCourseDescription;
import com.atguigu.eduService.entity.frontVo.FrontCourseQueryVo;
import com.atguigu.eduService.entity.vo.CourseInfoForm;
import com.atguigu.eduService.entity.vo.CoursePublishVo;
import com.atguigu.eduService.entity.frontVo.CourseWebVo;
import com.atguigu.eduService.mapper.EduCourseMapper;
import com.atguigu.eduService.service.EduCourseDescriptionService;
import com.atguigu.eduService.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-25
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
   @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;
    @Override
    public String InsertCourse(CourseInfoForm courseInfoForm) {
         //            将表单中的数据添加到数据库中。由于表单中的数据涉及到多个表。所以要存到多个表中。
        EduCourse eduCourse = new EduCourse();
        //        将两个表中都有的属性添加进eduCourse中。
        BeanUtils.copyProperties(courseInfoForm,eduCourse);

           baseMapper.insert(eduCourse);

//           由于课程基本信息和课程描述信息是一对一的。所以分别添加到两个表的数据的id要一样。
        String id = eduCourse.getId();

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
         BeanUtils.copyProperties(courseInfoForm,eduCourseDescription);
          eduCourseDescription.setId(id);
         eduCourseDescriptionService.save(eduCourseDescription);

         return id;
    }

    @Override
    public void updateCourse(CourseInfoForm courseInfoForm) {
//            修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        baseMapper.updateById(eduCourse);

//        修改描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoForm,eduCourseDescription);
        eduCourseDescription.setId(eduCourse.getId());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public CourseInfoForm getCourseInfo(String courseId) {
        CourseInfoForm courseInfoForm = new CourseInfoForm();

//        根据ID查出课程表信息
        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse,courseInfoForm);
//        根据ID查出描述表信息
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        BeanUtils.copyProperties(courseDescription,courseInfoForm);

        return courseInfoForm;

    }

    @Override
    public CoursePublishVo getCoursePublishInfo(String courseId) {
        CoursePublishVo coursePublishVo = baseMapper.selectCoursePublishById(courseId);
        return coursePublishVo;
    }

    @Override
    public Map<String, Object> getCoursePageByCondition(Page<EduCourse> coursePage, FrontCourseQueryVo frontCourseQueryVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(frontCourseQueryVo.getSubjectParentId())){
            wrapper.eq("subject_parent_id",frontCourseQueryVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(frontCourseQueryVo.getSubjectId())){
            wrapper.eq("subject_id",frontCourseQueryVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(frontCourseQueryVo.getBuyCountSort())){
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(frontCourseQueryVo.getGmtCreateSort())){
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(frontCourseQueryVo.getPriceSort())){
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(coursePage,wrapper);

        List<EduCourse> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;

    }

    @Override
    public CourseWebVo getBaseCourseInfo(String cid) {
        CourseWebVo courseWebVo = baseMapper.selectInfoWebById(cid);
        return courseWebVo;
    }


}
