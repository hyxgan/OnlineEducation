package excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @auther hyx
 */
public class ExcelListener extends AnalysisEventListener<Demodata> {
//    一行一行的读取excel内容，然后每读取一次都会执行invoke方法。
    @Override
    public void invoke(Demodata demodata, AnalysisContext analysisContext) {
        System.out.println("***" +demodata);

    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头"+headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
