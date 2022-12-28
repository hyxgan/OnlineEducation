package excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther hyx
 */
public class TestEasyExcelWrite {
    public static void main(String[] args) {
//         实现对excel的写操作
//        1. 设置写入文件夹地址和excel文件名称
         String fileName = "E:\\write.xlsx";

//         使用easyExcel里面的方法实现写操作
        EasyExcel.write(fileName,Demodata.class).sheet("学生表").doWrite(getData());


    }

    public static List<Demodata> getData(){
        List<Demodata> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Demodata demodata = new Demodata();
            demodata.setSid(i);
            demodata.setSname("lucy"+ i);
            list.add(demodata);
        }
        return list;
    }
}
