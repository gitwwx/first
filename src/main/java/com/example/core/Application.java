package com.example.core;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@MapperScan({"com.soouya.overseas.pay.common.mapper"})
@SpringBootApplication(scanBasePackages = "com.example.core")
@Slf4j
public class Application {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(Application.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        log.info("\n----------------------------------------------------------\n\t"
                + "Application " + env.getProperty("spring.application.name") + " is running! Access URLs:\n\t"
                + "Local: \t\thttp://localhost:" + port + path + "\n\t"
                + "External: \thttp://" + ip + ":" + port + path + "\n\t"
                + "当前环境: \t" + env.getProperty("spring.profiles.active") + "\n\t"
                + "Nacos连接地址: \t" + env.getProperty("spring.cloud.nacos.config.server-addr") + "\n\t"
                + "Redis用户名: \t" + env.getProperty("spring.redis.username") + "\n\t"
                + "数据库用户名: \t" + env.getProperty("spring.datasource.username") + "\n"
                + "----------------------------------------------------------");
    }

}

