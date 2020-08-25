package cn.rui.service;

//服务接口,实现写在provider里

import cn.rui.entity.CheckItemDeleteFailException;
import cn.rui.entity.PageResult;
import cn.rui.entity.QueryPageBean;
import cn.rui.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {

    //添加检查项
    public void add(CheckItem checkItem);

    //分页查询检查项
    public PageResult findPage(QueryPageBean queryPageBean);


    //删除检查项
    void delete(Integer id) throws CheckItemDeleteFailException;

    //检查项数据回显
    CheckItem findById(Integer id);

    //编辑检查项
    void updateById(CheckItem checkItem);

    //查询所有检查项
    List<CheckItem> findAll();



}
