package cn.rui.service.impl;

import cn.rui.entity.CheckItemDeleteFailException;
import cn.rui.entity.PageResult;
import cn.rui.entity.QueryPageBean;
import cn.rui.mapper.CheckItemMapper;
import cn.rui.pojo.CheckItem;
import cn.rui.service.CheckItemService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 检查项服务实现类
 *
 * @author 徽州大都督
 * @date 2020/8/21
 */
@Service  //这里要暴露服务，要选择dubbo里的service注解
@org.springframework.stereotype.Service
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    //注入dao对象
    @Autowired
    private CheckItemMapper checkItemMapper;

    //添加检查项
    @Override
    public void add(CheckItem checkItem) {
        checkItemMapper.add (checkItem);
    }

    //分页查询
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        Integer currentPage = queryPageBean.getCurrentPage ();
        Integer pageSize = queryPageBean.getPageSize ();
        String queryString = queryPageBean.getQueryString ();

        //mybatis分页助手
        PageHelper.startPage (currentPage, pageSize);

        Page<CheckItem> page = checkItemMapper.selectByCondition (queryString);
        long total = page.getTotal ();
        List<CheckItem> checkItemList = page.getResult ();
        PageResult pageResult = new PageResult (total, checkItemList);

        return pageResult;
    }


    @Override
    //删除检查项
    public void delete(Integer id) throws CheckItemDeleteFailException{

        //判断检查项是否已经关联到检查组
        long account = checkItemMapper.findAccount (id);
        //System.out.println (account);
        if (account > 0) {
            //说明已经关联，不允许删除
            throw new CheckItemDeleteFailException ();
        }

        checkItemMapper.delete (id);


    }


    //检查项数据回显
    public CheckItem findById(Integer id) {
        return checkItemMapper.findById (id);

    }

    @Override
    //编辑检查项
    public void updateById(CheckItem checkItem) {
        checkItemMapper.updateById (checkItem);
    }

    @Override
    //查询所有检查项
    public List<CheckItem> findAll() {

        return checkItemMapper.findAll();
    }


}
