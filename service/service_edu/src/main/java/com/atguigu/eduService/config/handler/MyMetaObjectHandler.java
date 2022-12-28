package com.atguigu.eduService.config.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @auther hyx
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

           setFieldValByName("gmtCreate",new Date(),metaObject);
           setFieldValByName("gmtModified",new Date(),metaObject);
           setFieldValByName("isDeleted",0,metaObject);
    }



    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("gmtModified",new Date(),metaObject);
    }
}
