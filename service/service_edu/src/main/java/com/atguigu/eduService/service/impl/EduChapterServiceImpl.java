package com.atguigu.eduService.service.impl;

import com.atguigu.eduService.entity.EduChapter;
import com.atguigu.eduService.entity.EduVideo;
import com.atguigu.eduService.entity.chapter.ChapterVo;
import com.atguigu.eduService.entity.chapter.VideoVo;
import com.atguigu.eduService.mapper.EduChapterMapper;
import com.atguigu.eduService.service.EduChapterService;
import com.atguigu.eduService.service.EduVideoService;
import com.atguigu.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-25
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
     @Autowired
    EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
//            查询课程对应的章节
        QueryWrapper<EduChapter> wrapperC = new QueryWrapper<>();
        wrapperC.eq("course_id",courseId);

        List<EduChapter> Chapters = baseMapper.selectList(wrapperC);

//        查章节对应的小节
        QueryWrapper<EduVideo> wrapperV = new QueryWrapper<>();
        wrapperV.eq("course_id",courseId);

        List<EduVideo> videos = eduVideoService.list(wrapperV);

//        将章节和小节封装到ChapterVo集合中
        ArrayList<ChapterVo> chapterVos = new ArrayList<>();

        for (EduChapter eduChapter :Chapters) {
//            获取数据库中章节表的数据，依次取出一个，将它对应的数据封装进chapterVo中
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);

            ArrayList<VideoVo> videoVos = new ArrayList<>();

            for (EduVideo eduVideo:videos) {
                if (eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setVideoVoList(videoVos);

            chapterVos.add(chapterVo);
        }
        return chapterVos;
    }

    @Override
    public boolean deleteChapterById(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(wrapper);
        if (count >0){//查询出小节，不进行删除
                throw new GuliException(20001,"不能删除");
        }
        else{   //没有小节，进行删除
            int rows = baseMapper.deleteById(chapterId);
            return rows>0;
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);

    }
}
