package com.atguigu.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 张于宴
 * @version 2019.3
 * @description excel实体类
 * @date 2022/7/27 16:25
 */
@Data
public class SubjectData {
    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
