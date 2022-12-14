package com.atguigu.eduorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication()
    @ComponentScan(basePackages = {"com.atguigu"})
    @MapperScan("com.atguigu.eduorder.mapper")
    @EnableDiscoveryClient//nacos注册
    @EnableFeignClients//服务发现
    @CrossOrigin
    public class OrderApplication {
        public static void main(String[] args) {
            SpringApplication.run(OrderApplication.class,args);
        }
}
