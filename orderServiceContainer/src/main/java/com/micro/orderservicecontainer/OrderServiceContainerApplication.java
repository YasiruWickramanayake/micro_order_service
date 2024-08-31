package com.micro.orderservicecontainer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.micro.orderService.*", "com.micro.orderservicecontainer.*"})
@EntityScan(basePackages = {"com.micro.orderService.*"})
@EnableJpaRepositories(basePackages = {"com.micro.orderService.*"})
public class OrderServiceContainerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceContainerApplication.class, args);
    }

}
