package cn.rui;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class BootHealthBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run (BootHealthBackendApplication.class, args);
    }

}
