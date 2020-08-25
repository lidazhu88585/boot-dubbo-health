package cn.rui.service.impl;

import cn.rui.entity.PageResult;
import cn.rui.entity.QueryPageBean;
import cn.rui.mapper.CheckGroupMapper;
import cn.rui.pojo.CheckGroup;
import cn.rui.service.CheckGroupService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 徽州大都督
 * @date 2020/8/24
 */
@Service
@org.springframework.stereotype.Service
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupMapper checkGroupMapper;

    @Override
    //新增检查组,同时还要关联检查项
    public void add(CheckGroup checkGroup, List<Integer> ids) {

        //新增检查组
        checkGroupMapper.add (checkGroup);
        Integer checkGroupId = checkGroup.getId (); //必须在配置文件配置才能获得

        if (!CollectionUtils.isEmpty (ids) && ids.size () > 0) {
            for (Integer checkitemId : ids) {
                Map<String, Integer> map = new HashMap<> ();
                map.put ("checkgroupId", checkGroupId);
                map.put ("checkitemId", checkitemId);
                checkGroupMapper.setCheckGroupIdAndCheckItemId (map);
            }
        }
    }

    @Override
    //检查组分页查询
    public PageResult findPage(QueryPageBean queryPageBean) {

        Integer currentPage = queryPageBean.getCurrentPage ();
        Integer pageSize = queryPageBean.getPageSize ();
        String queryString = queryPageBean.getQueryString ();
        //mybatis分页助手
        PageHelper.startPage (currentPage, pageSize);
        Page<CheckGroup> page = checkGroupMapper.selectByCondition (queryString);
        long total = page.getTotal ();
        List<CheckGroup> checkGroupList = page.getResult ();

        PageResult pageResult = new PageResult (total, checkGroupList);

        return pageResult;
    }

    @Override
    //根据id查询检查组
    public CheckGroup findById(Integer id) {
        return checkGroupMapper.findById (id);

    }

    @Override
    //根据检查组的id查询所有关联的检查项id
    public List<Integer> findCheckItemById(Integer checkgroupId) {
        List<Integer> ids = checkGroupMapper.findCheckItemById (checkgroupId);
        return ids;
    }

    @Override
    //编辑检查组
    public void update(List<Integer> ids, CheckGroup checkGroup) {

        //修改检查组
        checkGroupMapper.updateCheckGroup (checkGroup);

        List<Integer> checkItemById = checkGroupMapper.findCheckItemById (checkGroup.getId ());

        if (!CollectionUtils.isEmpty (checkItemById) && checkItemById.size () > 0) {
            //删除检查组关联的检查项
            for (Integer id : checkItemById) {
                checkGroupMapper.deleteById (id);
            }
        }

        //关联新的检查项
        if (!CollectionUtils.isEmpty (ids) && ids.size () > 0) {

            for (Integer id : ids) {
                Map<String, Integer> map = new HashMap<> ();
                map.put ("checkgroupId", checkGroup.getId ());
                map.put ("checkitemId", id);
                checkGroupMapper.setCheckGroupIdAndCheckItemId (map);
            }


        }


    }

    @Override
    //根据id删除检查组和关联的检查项
    public void deleteById(Integer id) {

        //删除关联的检查项
        checkGroupMapper.deleteByGroupId (id);

        //删除检查组
        checkGroupMapper.deleteCheckGroupById (id);


    }

    @Override
    //查询所有检查组
    public List<CheckGroup> findAll() {
        return checkGroupMapper.findAll ();

    }
}
