package cn.rui.controller;

import cn.rui.constant.MessageConstant;
import cn.rui.entity.CheckItemDeleteFailException;
import cn.rui.entity.PageResult;
import cn.rui.entity.QueryPageBean;
import cn.rui.entity.Result;
import cn.rui.pojo.CheckItem;
import cn.rui.service.CheckItemService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 检查项管理
 */

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference  //查找服务
    private CheckItemService checkItemService;

    //新增检查项
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {


        try {
            checkItemService.add (checkItem);
        } catch (Exception e) {
            e.printStackTrace ();
            //服务调用失败
            return new Result (false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result (true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }


    //分页查询
    @PostMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return checkItemService.findPage (queryPageBean);
    }

    //删除检查项
    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        try {
            checkItemService.delete (id);
        } catch (Exception e) {
            e.printStackTrace ();
            if (e instanceof CheckItemDeleteFailException){
                return new Result (false,MessageConstant.DELETE_FAIL_CHECKITEM);
            }
            return new Result (false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }

        return new Result (true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    //编辑数据回显
    @GetMapping("findById/{id}")
    public Result findById(@PathVariable("id") Integer id) {

        CheckItem checkItem ;
        try {
            checkItem = checkItemService.findById (id);
        } catch (Exception e) {
            e.printStackTrace ();
            return new Result (false,null,null);
        }

        return new Result (true,null,checkItem);
    }


    //更改检查项
    @PutMapping("update")
    public Result updateById(@RequestBody CheckItem checkItem){

        try {
            checkItemService.updateById(checkItem);
        } catch (Exception e) {
            return new Result (false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }

        return new Result (true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    //查询所有检查项
    @GetMapping("findAll")
    public Result findAll(){
        try {
            List<CheckItem> checkItemList= checkItemService.findAll();

            return new Result (true,null,checkItemList);
        } catch (Exception e) {
            e.printStackTrace ();
            return new Result (false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }


}
