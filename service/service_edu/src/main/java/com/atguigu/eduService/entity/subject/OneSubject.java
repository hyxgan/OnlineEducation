package com.atguigu.eduService.entity.subject;

import lombok.Data;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

/**
 * @auther hyx
 */
@Data
public class OneSubject {

    private String id;

    private String title;

    List<TwoSubject> twosubject;
}
