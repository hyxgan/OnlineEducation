package com.atguigu.eduService.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduService.entity.EduTeacher;
import com.atguigu.eduService.entity.vo.TeacherQuery;
import com.atguigu.eduService.service.EduTeacherService;
import com.atguigu.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-17
 */

@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduService/edu-teacher")
//@CrossOrigin
public class EduTeacherController {

    @Autowired
    EduTeacherService eduTeacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAll(){
      /*  try{
            int i = 1/0;
        }catch (Exception e){
            throw new GuliException(2000,"自定义异常");
        }*/

        List<EduTeacher> list = eduTeacherService.list(null);
             return R.ok().data("items",list);

    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R deleteTeacher(@PathVariable String id){
        boolean boo = eduTeacherService.removeById(id);
         if (boo){
             return R.ok();
         }
         else{
             return R.error();
         }
    }
    @ApiOperation(value = "分页查询")
    @GetMapping("{current},{limit}")
    public R selectPage(@PathVariable Long current , @PathVariable Long limit){
        Page<EduTeacher> page = new Page<>(current, limit);
        eduTeacherService.page(page,null);
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();  //数据list集合


        return R.ok().data("total",total).data("rows",records);

    }

//    测试多条件分页模糊查询
//    requestBody : 使用JSON传递数据，把JSON数据封装到对应对象里面。 需要使用PostMapping.
//    responseBody : 返回JSON数据。
   @ApiOperation(value = "多条件模糊查询")
    @PostMapping("/testConditionLikeQuery/{current}/{limit}")
    public R testConditionLikeQuery(@PathVariable Long current,
                                    @PathVariable Long limit,
                                    @RequestBody(required = false) TeacherQuery teacherQuery
                                    ){
        Page<EduTeacher> page = new Page<>(current,limit);
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.like("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.like("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.like("gmt_modified",end);

        }
        wrapper.orderByDesc("gmt_create");
        eduTeacherService.page(page,wrapper);

        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();

       return R.ok().data("total",total).data("rows",records);

    }

    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean boo = eduTeacherService.save(eduTeacher);
        if (boo){
            return R.ok();
        }
        else{ return R.error();}

    }

    @ApiOperation(value = "通过ID查讲师")
     @GetMapping("findById/{id}")
    public R QueryTeacherById(@PathVariable Long id){
         EduTeacher teacher = eduTeacherService.getById(id);
         return R.ok().data("teacher",teacher);
     }

     @ApiOperation(value = "修改讲师")
     @PostMapping("updateTeacher")
      public R updateTeacher(@RequestBody EduTeacher eduTeacher){
         boolean flag = eduTeacherService.updateById(eduTeacher);
         if (flag){
             return R.ok();
         }
         else{
             return R.error();
         }
     }



}

