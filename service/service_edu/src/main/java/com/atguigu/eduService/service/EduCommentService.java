package com.atguigu.eduService.service;

import com.atguigu.eduService.entity.EduComment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-12-13
 */
public interface EduCommentService extends IService<EduComment> {

    Map<String, Object> selectCommentList(Page<EduComment> page);
}
