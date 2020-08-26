package cn.rui;

import cn.rui.pojo.Setmeal;
import cn.rui.service.SetmealService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BootHealthTimingtaskApplicationTests {

    @Reference
    private SetmealService setmealService;

    @Test
    void contextLoads() {

        Setmeal byId = setmealService.findById (12);
        System.out.println (byId);
    }

}
