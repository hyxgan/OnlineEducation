package com.atguigu.eduService.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduService.entity.subject.OneSubject;
import com.atguigu.eduService.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-24
 */
@RestController
@RequestMapping("/eduService/subject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    EduSubjectService eduSubjectService;

//    添加课程分类
    @PostMapping("addSubject")
      public R addSubject(MultipartFile file){
            eduSubjectService.saveSubject(file,eduSubjectService);
            return R.ok();
      }

//     获取课程列表
     @GetMapping("getOneTwoSubject")
     public R getAllOneTwoSubject(){
         List<OneSubject> list = eduSubjectService.getOneTwoSubject();

         return R.ok().data("list",list);

     }

}

