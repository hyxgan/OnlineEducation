package com.atguigu.eduService.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduService.entity.EduChapter;
import com.atguigu.eduService.entity.chapter.ChapterVo;
import com.atguigu.eduService.service.EduChapterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/eduService/chapter")
//@CrossOrigin
public class EduChapterController {
    @Autowired
    EduChapterService eduChapterService;

    @GetMapping("getAllChapter/{courseId}")
    public R getAllChapter(@PathVariable("courseId") String courseId){
        List<ChapterVo> chapterVoList = eduChapterService.getChapterVideoByCourseId(courseId);

        return R.ok().data("chapterList",chapterVoList);
    }

//  添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        boolean save = eduChapterService.save(eduChapter);
        if (save){
            return R.ok();
        }
        else{
            return R.error();
        }
    }

//    修改章节
    @PutMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("id",eduChapter.getId());
        boolean update = eduChapterService.update(eduChapter, wrapper);
        if (update){
            return R.ok();
        }
        else{
            return R.error();
        }
    }


//    删除章节，如果章节中有小节，提示不能删除
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable("chapterId") String chapterId){
        boolean b = eduChapterService.deleteChapterById(chapterId);
        if (b){
            return R.ok();
        }
        else{
            return R.error();
        }
    }

//    根据ID查询章节
    @GetMapping("getChapterById/{chapterId}")
    public R getChapter(@PathVariable("chapterId") String chapterId){
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("Chapter",eduChapter);
    }
}

