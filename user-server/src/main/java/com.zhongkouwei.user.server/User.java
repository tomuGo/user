package com.zhongkouwei.user.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDiscoveryClient
@EnableFeignClients(value = "com.zhongkouwei")
@SpringBootApplication(scanBasePackages ="com.zhongkouwei.user.server")
@EnableJpaRepositories("com.zhongkouwei.user.server.reporitory")
@EntityScan(basePackages = {"com.zhongkouwei.user"})
public class User {
    public static void main(String[] args) {
        SpringApplication.run(User.class, args);
    }
}
