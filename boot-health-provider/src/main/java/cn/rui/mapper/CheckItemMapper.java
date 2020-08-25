package cn.rui.mapper;

import cn.rui.pojo.CheckItem;
import com.github.pagehelper.Page;

import java.util.List;

//对应的映射文件写在resources里
public interface CheckItemMapper {

    public void add(CheckItem checkItem);

    public Page<CheckItem> selectByCondition(String queryString);

    void delete(Integer id);

    CheckItem findById(Integer id);

    void updateById(CheckItem checkItem);

    public long findAccount(int id);

    List<CheckItem> findAll();
}
