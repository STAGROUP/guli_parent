package com.atguigu.oss;

        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
        import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
        import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
        import org.springframework.context.annotation.ComponentScan;

/**
 * @author 张于宴
 * @version 2019.+++
 * @description
 * @date 2022/7/25 22:39
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class )
@ComponentScan(basePackages = {"com.atguigu"})
@EnableDiscoveryClient//nacos注册
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }

}
