package com.nexio.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class NexioServiceDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(
            NexioServiceDiscoveryApplication.class, args
        );
    }
}