package com.atguigu.eduService.service;

import com.atguigu.eduService.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-17
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String,Object> getFrontTeacherList(Page<EduTeacher> page);

    EduTeacher getTeacherByCid(String teacherId);
}
