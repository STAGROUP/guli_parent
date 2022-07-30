package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 张于宴
 * @version 2019.3
 * @description
 * @date 2022/7/27 13:58
 */

public class TestEasyExcel {
    public static void main(String[] args) {
      //实现excel读操作
        String fileName = "D:\\easyExcel.xlsx";
        //路径名称 实体类class 监听器
        EasyExcel.read(fileName,DemoData.class,new ExcelListener()).sheet().doRead();
    }

}
