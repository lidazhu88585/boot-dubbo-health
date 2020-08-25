package cn.rui.mapper;

import cn.rui.pojo.CheckGroup;
import cn.rui.pojo.CheckItem;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface CheckGroupMapper {
    void add(CheckGroup checkGroup);

    void setCheckGroupIdAndCheckItemId(Map<String, Integer> map);

    Page<CheckGroup> selectByCondition(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemById(Integer id);

    void updateCheckGroup(CheckGroup checkGroup);

    void deleteById(Integer id);

    void deleteCheckGroupById(Integer id);

    void deleteByGroupId(Integer id);

    List<CheckGroup> findAll();
}
