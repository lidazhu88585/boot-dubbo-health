package cn.rui;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("cn.rui.mapper")
@EnableDubbo
public class BootHealthProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run (BootHealthProviderApplication.class, args);
    }

}
