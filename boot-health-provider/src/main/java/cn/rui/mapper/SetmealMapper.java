package cn.rui.mapper;

import cn.rui.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SetmealMapper {
    void add(Setmeal setmeal);

    void setCheckGroupIdAndSetMealId(Map<String, Integer> map);

    Page<Setmeal> selectByCondition(String queryString);

    Setmeal findById(Integer id);

    List<Integer> findBySetMealId(Integer id);

    void update(Setmeal setmeal);

    void deleteBySetMealId(Integer id);

    Set<String> findImgList();
}
