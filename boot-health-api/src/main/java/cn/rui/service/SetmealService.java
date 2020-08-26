package cn.rui.service;

import cn.rui.entity.PageResult;
import cn.rui.entity.QueryPageBean;
import cn.rui.pojo.Setmeal;

import java.util.List;
import java.util.Set;

public interface SetmealService {

    //新增套餐
    void add(List<Integer> ids, Setmeal setmeal);

    //套餐分页查询
    PageResult findPage(QueryPageBean queryPageBean);

    //根据id查询套餐信息
    Setmeal findById(Integer id);

    //根据套餐id查询所有关联的检查组id集合
    List<Integer> findBySetMealId(Integer id);

    //编辑套餐
    void update(List<Integer> ids, Setmeal setmeal);

    //查询套餐中所有的图片集合
    Set<String> findImgList();

    //删除套餐
    void deleteById(Integer id);
}
