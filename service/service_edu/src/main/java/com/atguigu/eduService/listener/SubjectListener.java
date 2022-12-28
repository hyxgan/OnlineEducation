package com.atguigu.eduService.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduService.entity.EduSubject;
import com.atguigu.eduService.entity.excel.SubjectData;
import com.atguigu.eduService.service.EduSubjectService;
import com.atguigu.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @auther hyx
 */
public class SubjectListener extends AnalysisEventListener<SubjectData> //监听器监听的是Excel文件对应的实体类
{
     EduSubjectService eduSubjectService;

    public SubjectListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null){
            throw new GuliException(20001,"文件数据为空");

        }

//        获取一级对象。
        EduSubject one = existOne(subjectData.getOneSubjectName());

        if(one == null){
//            如果没有，创建一级对象。 总之，执行完if后，one都有值。
            one = new EduSubject();
            one.setTitle(subjectData.getOneSubjectName());
            one.setParentId("0");
            eduSubjectService.save(one);
        }

//        pid 就是一级分类的ID。而在上面获取了一级分类的对象。可以通过对象获取ID值。 如果一级分类不存在。那么在进行判断，然后添加之后,也就有了。
//
        String pid = one.getId();
        EduSubject two = existTwo(subjectData.getTwoSubjectName(),pid);
        if (two == null){
            EduSubject eduSubject = new EduSubject();
            eduSubject.setTitle(subjectData.getTwoSubjectName());
            eduSubject.setParentId(pid);
            eduSubjectService.save(eduSubject);

        }

    }
    //                判断一级分类不能重复添加
    public EduSubject existOne(String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",0);
        EduSubject eduSubject = eduSubjectService.getOne(wrapper);
        return eduSubject;
    }

    //                判断二级分类不能重复添加
    public EduSubject existTwo(String name , String pid){
        QueryWrapper<EduSubject> Wrapper = new QueryWrapper<>();
         Wrapper.eq("title",name);
         Wrapper.eq("parent_id",pid);

        EduSubject eduSubject = eduSubjectService.getOne(Wrapper);
        return eduSubject;
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
