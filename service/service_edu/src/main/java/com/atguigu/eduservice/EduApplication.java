package com.atguigu.eduservice;

        import com.google.gson.internal.$Gson$Preconditions;
        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
        import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
        import org.springframework.cloud.openfeign.EnableFeignClients;
        import org.springframework.context.annotation.ComponentScan;

/**
 * @author 张于宴
 * @version 2019.3
 * @description 启动类
 * @date 2022/6/28 15:19
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"})
@EnableDiscoveryClient//nacos注册
@EnableFeignClients//服务调用
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }

}

