package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
//熔断器容错处理方法 正确不会执行 出错后才会执行
public class VodFileDegradeFeignClient implements vodClient {
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除视频出错");
    }

    @Override
    public R deleteBatch(List<String> videoList) {
        return R.error().message("删除多个视频出错");
    }
}
