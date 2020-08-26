package cn.rui.timingtask;

import cn.rui.constant.RedisConstant;
import cn.rui.service.SetmealService;
import cn.rui.utils.QiniuUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 定时清理七牛云垃圾图片
 * @author 徽州大都督
 * @date 2020/8/26
 */
@EnableScheduling
@Component
@Slf4j
public class RedisTimingClear {

    @Reference
    private SetmealService setmealService;
    @Autowired
    private RedisTemplate redisTemplate;

    //每隔5秒执行一次：*/5 * * * * ?

    //每隔1分钟执行一次：0 */1 * * * ?

    //每天23点执行一次：0 0 23 * * ?

    //每天凌晨1点执行一次：0 0 1 * * ?

    //每月1号凌晨1点执行一次：0 0 1 1 * ?

    //每月最后一天23点执行一次：0 0 23 L * ?

    //每周星期天凌晨1点实行一次：0 0 1 ? * L

    //在26分、29分、33分执行一次：0 26,29,33 * * * ?

    //每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?

    //每天凌晨12点执行一次:  0 0 0 * * ?

    //每隔5分钟执行一次：0 0/5 * * * ?
    @Scheduled(cron = "0 0 0 * * ?")
    public void DeleteImg(){

        if (redisTemplate.opsForValue ().get (RedisConstant.IMG_SAVE)!=null){
            Set<String> imgList= (Set<String>) redisTemplate.opsForValue ().get (RedisConstant.IMG_SAVE);

            //查询t_setmeal数据库中图片集合
            Set<String> baseList=setmealService.findImgList();
            for (String img : imgList) {
                if (!baseList.contains (img)){
                    //将数据库中不包含图片删除
                    QiniuUtils.deleteFileFromQiniu (img);
                    log.info ("删除了图片:"+img);
                }
            }

            redisTemplate.delete (RedisConstant.IMG_SAVE);

        }


        log.info ("定时任务触发了...");
    }
}
