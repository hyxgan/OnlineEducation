package com.atguigu.eduService.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduService.entity.EduComment;
import com.atguigu.eduService.entity.EduCourse;
import com.atguigu.eduService.service.EduCommentService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-12-13
 */
@RestController
@RequestMapping("/eduService/educomment")
//@CrossOrigin
public class EduCommentController {
        @Autowired
    EduCommentService eduCommentService;

    @PostMapping("addComment")
    public R addComment(@RequestBody EduComment eduComment){
        boolean save = eduCommentService.save(eduComment);
        if (save){
            return R.ok();
        }
        else {
            return R.error();
        }
    }

    @GetMapping("getCommentPage/{current}/{limit}")
    public R getCommentPage(@PathVariable("current") Long current,@PathVariable("limit") Long limit){
        Page<EduComment> page = new Page<>(current, limit);
        Map<String,Object> map =  eduCommentService.selectCommentList(page);

        return R.ok().data(map);
    }
}

