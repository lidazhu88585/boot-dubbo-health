package cn.rui.controller;

import cn.rui.constant.MessageConstant;
import cn.rui.constant.RedisConstant;
import cn.rui.entity.PageResult;
import cn.rui.entity.QueryPageBean;
import cn.rui.entity.Result;
import cn.rui.pojo.Setmeal;
import cn.rui.service.SetmealService;
import cn.rui.utils.QiniuUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 体验套餐
 *
 * @author 徽州大都督
 * @date 2020/8/25
 */
@RestController
@RequestMapping("setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private RedisTemplate redisTemplate;

    //图片上传
    @PostMapping("upload")
    public Result upload(MultipartFile imgFile) {

        String UUID = java.util.UUID.randomUUID ().toString (); //随机获取名称

        String oldName = imgFile.getOriginalFilename ();  //文件原始文件名
        int index = oldName.lastIndexOf (".");//获得文件后缀所在的下标
        String ext = oldName.substring (index - 1);  // .jpg
        String newName = UUID + ext;

        //使用封装的七牛云工具类上传到云服务器
        try {
            QiniuUtils.upload2Qiniu (imgFile.getBytes (), newName);

            //将图片名称保存到redis中
            if (redisTemplate.opsForValue ().get (RedisConstant.IMG_SAVE) == null) {
                Set<String> imgList = new HashSet<> ();
                imgList.add (newName);
                redisTemplate.opsForValue ().set (RedisConstant.IMG_SAVE, imgList,60, TimeUnit.DAYS);
            } else {
                Set<String> imgList = (Set<String>) redisTemplate.opsForValue ().get (RedisConstant.IMG_SAVE);
                imgList.add (newName);
                redisTemplate.opsForValue ().set (RedisConstant.IMG_SAVE, imgList,60, TimeUnit.DAYS);
            }

        } catch (IOException e) {
            e.printStackTrace ();
            return new Result (false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result (true, MessageConstant.PIC_UPLOAD_SUCCESS, newName);
    }

    //添加套餐
    @PostMapping("add/{ids}")
    public Result add(@PathVariable("ids") List<Integer> ids, @RequestBody Setmeal setmeal) {

        try {
            setmealService.add (ids, setmeal);
        } catch (Exception e) {
            e.printStackTrace ();
            return new Result (false, MessageConstant.ADD_SETMEAL_FAIL);
        }

        return new Result (true, MessageConstant.ADD_SETMEAL_SUCCESS);

    }

    //套餐分页查询
    @PostMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {

        return setmealService.findPage (queryPageBean);

    }

    //根据id查询套餐信息
    @GetMapping("findById/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        try {
            Setmeal setmeal = setmealService.findById (id);
            return new Result (true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace ();
            return new Result (false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    //根据套餐id查询所有关联的检查组id集合
    @GetMapping("findBySetMealId/{id}")
    public List<Integer> findBySetMealId(@PathVariable("id") Integer id) {
        return setmealService.findBySetMealId (id);
    }

    //编辑套餐
    @PostMapping("update/{ids}")
    public Result update(@PathVariable("ids") List<Integer> ids, @RequestBody Setmeal setmeal) {
        try {
            setmealService.update (ids, setmeal);
        } catch (Exception e) {
            e.printStackTrace ();
            return new Result (false, MessageConstant.EDIT_SETMEAL_FAIL);
        }

        return new Result (true, MessageConstant.EDIT_SETMEAL_SUCCESS);
    }


    //删除套餐
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable("id")Integer id){
        try {
            setmealService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace ();
            return new Result (false,MessageConstant.DELETE_SETMEAL_FAIL);
        }

        return new Result (true,MessageConstant.DELETE_SETMEAL_SUCCESS);
    }
}
