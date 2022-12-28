package com.atguigu.eduService.entity.chapter;

import lombok.Data;

import java.util.List;

/**
 * @auther hyx
 */
@Data
public class ChapterVo {
    private String id;
    private String title;
    private List<VideoVo> videoVoList;
}
