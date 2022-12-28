package com.atguigu.eduService.service;

import com.atguigu.eduService.entity.EduSubject;
import com.atguigu.eduService.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.Subject;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-24
 */
public interface EduSubjectService extends IService<EduSubject> {
//添加课程分类
    void saveSubject(MultipartFile file,EduSubjectService eduSubjectService);

    List<OneSubject> getOneTwoSubject();

    EduSubject getOneSubjectByCid(String cid);

    EduSubject getTwoSubjectByCid(String cid);
}
