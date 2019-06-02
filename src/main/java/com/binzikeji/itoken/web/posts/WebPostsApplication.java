package com.binzikeji.itoken.web.posts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description
 * @Author Bin
 * @Date 2019/4/26 9:47
 **/
@SpringBootApplication(scanBasePackages = "com.binzikeji.itoken")
@EnableDiscoveryClient
@EnableFeignClients
public class WebPostsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebPostsApplication.class, args);
    }
}
