package com.atguigu.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 张于宴
 * @version 2019.3
 * @description easyexcel
 * @date 2022/7/27 13:55
 */

@Data
public class DemoData {
    //设置表头名称
    @ExcelProperty(value = "一级分类",index = 0)
    private String sno;
    @ExcelProperty(value = "二级分类",index = 1)
    private String sname;
}
