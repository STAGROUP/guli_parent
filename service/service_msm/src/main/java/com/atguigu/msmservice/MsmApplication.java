package com.atguigu.msmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
    @ComponentScan(basePackages = {"com.atguigu"})
    @EnableDiscoveryClient//nacos注册
    @CrossOrigin
    public class MsmApplication {
        public static void main(String[] args) {
            SpringApplication.run(MsmApplication.class,args);
        }
}
