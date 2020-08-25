package cn.rui.controller;

import cn.rui.constant.MessageConstant;
import cn.rui.entity.PageResult;
import cn.rui.entity.QueryPageBean;
import cn.rui.entity.Result;
import cn.rui.pojo.CheckGroup;
import cn.rui.service.CheckGroupService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 检查组管理
 * @author 徽州大都督
 * @date 2020/8/24
 */
@RestController
@RequestMapping("checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    //添加检查组信息
    @PostMapping("add/{ids}")
    public Result save(@PathVariable("ids") List<Integer> ids, @RequestBody CheckGroup checkGroup) {

        try {
            checkGroupService.add (checkGroup, ids);
        } catch (Exception e) {
            e.printStackTrace ();
            return new Result (false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }

        return new Result (true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    //检查组分页查询
    @PostMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return checkGroupService.findPage (queryPageBean);
    }

    //根据id查询检查组
    @GetMapping("findById/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        try {
            CheckGroup checkGroup = checkGroupService.findById (id);
            return new Result (true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
        } catch (Exception e) {
            e.printStackTrace ();
            return new Result (false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    //根据检查组的id查询所有关联的检查项id
    @GetMapping("findCheckItemById/{checkgroupId}")
    public List<Integer> findCheckItemById(@PathVariable("checkgroupId") Integer checkgroupId) {


        List<Integer> ids = checkGroupService.findCheckItemById (checkgroupId);

        return ids;

    }

    //编辑检查组
    @PostMapping("update/{ids}")
    public Result update(@PathVariable("ids")List<Integer> ids,@RequestBody CheckGroup checkGroup){

        /*System.out.println (ids);
        System.out.println (checkGroup);*/

        try {
            checkGroupService.update(ids,checkGroup);
            return new Result (true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace ();
            return new Result (false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    //根据id删除检查组和关联的检查项
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable("id")Integer id){
        try {
            checkGroupService.deleteById(id);
            return new Result (true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace ();
            return new Result (false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    //查询所有检查组
    @GetMapping("findAll")
    public Result findAll(){
        try {
            List<CheckGroup> checkGroupList=checkGroupService.findAll();
            return new Result (true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroupList);
        } catch (Exception e) {
            e.printStackTrace ();

            return new Result (false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

}
