package com.atguigu.eduorder.service.impl;

import com.atguigu.eduorder.entity.TPayLog;
import com.atguigu.eduorder.mapper.TPayLogMapper;
import com.atguigu.eduorder.service.TPayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author 张于宴
 * @since 2022-12-28
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {

    @Override
    public Map createNatvie(String orderNo) {

        return null;
    }
}
