package excel;

import com.alibaba.excel.EasyExcel;

/**
 * @auther hyx
 */
public class testEasyExcelread{
    public static void main(String[] args) {
        // 创建要读取文件的路径
        String fileName = "E:\\write.xlsx";

// 读取
        EasyExcel.read(fileName,Demodata.class,new ExcelListener()).sheet().doRead();

    }

}
