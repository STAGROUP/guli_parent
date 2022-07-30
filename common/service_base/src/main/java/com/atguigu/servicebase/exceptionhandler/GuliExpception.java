package com.atguigu.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 张于宴
 * @version 2019.3
 * @description 自定义异常类
 * @date 2022/7/1 14:45
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliExpception extends RuntimeException {
    private Integer code;//状态码
    private String msg;//异常信息

}
