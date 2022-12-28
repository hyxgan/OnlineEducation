package com.atguigu.eduService.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduService.entity.EduSubject;
import com.atguigu.eduService.entity.excel.SubjectData;
import com.atguigu.eduService.entity.subject.OneSubject;
import com.atguigu.eduService.entity.subject.TwoSubject;
import com.atguigu.eduService.listener.SubjectListener;
import com.atguigu.eduService.mapper.EduSubjectMapper;
import com.atguigu.eduService.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-24
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        InputStream in = null;
        try {
            in = file.getInputStream();
//            注意； sheet和doread一定要写不然不会读。
            EasyExcel.read(in, SubjectData.class,new SubjectListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<OneSubject> getOneTwoSubject() {
//      ServiceImpl中封装了baseMapper , 就相当于当前类，可以调用各种方法
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        //          获取一级目录
            List<EduSubject> oneList = baseMapper.selectList(wrapperOne);

//        获取二级目录
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
         wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoList = baseMapper.selectList(wrapperTwo);

//        封装到一个集合中
        ArrayList<OneSubject> oneSubjects = new ArrayList<>();

//      遍历一级目录
        for ( EduSubject one : oneList) {
//            创建一级目录的实体类，然后把一级目录的值存到里面
            OneSubject oneSubject = new OneSubject();
           /*这个方法可以，但是有工具类可以更方便
            oneSubject.setId(one.getId());
            oneSubject.setTitle(one.getTitle());*/

//            找到one中对应属性封装到oneSubject中
            BeanUtils.copyProperties(one,oneSubject);
//            创建一个TwoSubject的集合，为一级目录的属性
            ArrayList<TwoSubject> twoSubjects = new ArrayList<>();

            for (EduSubject two : twoList) {
//                遍历二级目录，看哪个是上面一级目录的子目录
//                String判断是否相等必须要用equals，不能用==
                if (two.getParentId().equals(one.getId())){

                    TwoSubject twoSubject = new TwoSubject();
              /*      twoSubject.setId(two.getId());
                    twoSubject.setTitle(two.getTitle());*/
                        BeanUtils.copyProperties(two,twoSubject);
                    twoSubjects.add(twoSubject);
                }

            }
//            将二级目录的集合设置到一级目录的属性中。
            oneSubject.setTwosubject(twoSubjects);

//            将一个一级目录存到集合中，最后返回。
            oneSubjects.add(oneSubject);
        }
       return oneSubjects;
    }

    @Override
    public EduSubject getOneSubjectByCid(String cid) {
        EduSubject eduSubject = baseMapper.selectById(cid);
        return eduSubject;
    }

    @Override
    public EduSubject getTwoSubjectByCid(String cid) {
        EduSubject eduSubject = baseMapper.selectById(cid);
        return eduSubject;
    }
}
