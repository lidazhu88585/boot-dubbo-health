package cn.rui.service;

import cn.rui.entity.PageResult;
import cn.rui.entity.QueryPageBean;
import cn.rui.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {

    //新增检查组
    void add(CheckGroup checkGroup, List<Integer> ids);

    //检查组分页查询
    PageResult findPage(QueryPageBean queryPageBean);

    //根据id查询检查组
    CheckGroup findById(Integer id);

    //根据检查组的id查询所有关联的检查项id
    List<Integer> findCheckItemById(Integer checkgroupId);

    //编辑检查组
    void update(List<Integer> ids, CheckGroup checkGroup);

    //根据id删除检查组和关联的检查项
    void deleteById(Integer id);

    //查询所有检查组
    List<CheckGroup> findAll();
}
