package com.atguigu.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @author 张于宴
 * @version 2019.3
 * @description 监听器
 * @date 2022/7/27 15:57
 */

public class ExcelListener extends AnalysisEventListener<DemoData> {
    //一行一行读取excel内容  data每行的内容
    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        System.out.println("----"+data);
    }
    //读取表头内容
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头"+headMap);
    }

    //读取完成之后
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
