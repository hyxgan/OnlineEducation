package excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @auther hyx
 */
@Data
public class Demodata {
   //    设置excel表头名称
    @ExcelProperty("学生id")
    private Integer sid;
    //    设置excel表头名称
    @ExcelProperty("学生姓名")
    private String sname;

}
