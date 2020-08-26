package cn.rui.service.impl;

import cn.rui.constant.RedisConstant;
import cn.rui.entity.PageResult;
import cn.rui.entity.QueryPageBean;
import cn.rui.mapper.SetmealMapper;
import cn.rui.pojo.CheckGroup;
import cn.rui.pojo.Setmeal;
import cn.rui.service.SetmealService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 徽州大都督
 * @date 2020/8/25
 */
@Service
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    //新增套餐
    public void add(List<Integer> ids, Setmeal setmeal) {

        //添加套餐
        setmealMapper.add (setmeal);
        //获取添加套餐的id
        Integer id = setmeal.getId ();  //必须在配置文件中配置


        //添加套餐和检查组关联
        if (!CollectionUtils.isEmpty (ids) && ids.size () > 0) {
            for (Integer checkgroupId : ids) {
                Map<String, Integer> map = new HashMap<> ();
                map.put ("checkgroupId", checkgroupId);
                map.put ("setmealId", id);
                setmealMapper.setCheckGroupIdAndSetMealId (map);
            }
        }


    }

    @Override
    //套餐分页查询
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage ();
        Integer pageSize = queryPageBean.getPageSize ();
        String queryString = queryPageBean.getQueryString ();

        PageHelper.startPage (currentPage, pageSize);
        Page<Setmeal> page = setmealMapper.selectByCondition (queryString);

        long total = page.getTotal ();
        List<Setmeal> setmealList = page.getResult ();

        PageResult pageResult = new PageResult (total, setmealList);

        return pageResult;
    }

    @Override
    //根据id查询套餐信息
    public Setmeal findById(Integer id) {
        return setmealMapper.findById (id);
    }

    @Override
    //根据套餐id查询所有关联的检查组id集合
    public List<Integer> findBySetMealId(Integer id) {

        return setmealMapper.findBySetMealId (id);
    }

    @Override
    //编辑套餐
    public void update(List<Integer> ids, Setmeal setmeal) {

        //将旧图片名称存入缓存
        Setmeal byId = setmealMapper.findById (setmeal.getId ());

        if (redisTemplate.opsForValue ().get (RedisConstant.IMG_SAVE)!=null){
            Set<String> imgList = (Set<String>) redisTemplate.opsForValue ().get (RedisConstant.IMG_SAVE);
            imgList.add (byId.getImg ());
            redisTemplate.opsForValue ().set (RedisConstant.IMG_SAVE,imgList,60, TimeUnit.DAYS);
        }else {
            Set<String> imgList = new HashSet<> ();
            imgList.add (byId.getImg ());
            redisTemplate.opsForValue ().set (RedisConstant.IMG_SAVE,imgList,60, TimeUnit.DAYS);
        }

        //更新套餐内容
        setmealMapper.update (setmeal);

        //根据套餐id删除关联的检查组
        setmealMapper.deleteBySetMealId (setmeal.getId ());

        //添加新的关联信息
        //添加套餐和检查组关联
        if (!CollectionUtils.isEmpty (ids) && ids.size () > 0) {
            for (Integer checkgroupId : ids) {
                Map<String, Integer> map = new HashMap<> ();
                map.put ("checkgroupId", checkgroupId);
                map.put ("setmealId", setmeal.getId ());
                setmealMapper.setCheckGroupIdAndSetMealId (map);
            }
        }
    }

    @Override
    //查询套餐里所有的图片集合
    public Set<String> findImgList() {

        return setmealMapper.findImgList();
    }

    @Override
    //删除套餐
    public void deleteById(Integer id) {

        //根据id查询套餐信息
        Setmeal setmeal = setmealMapper.findById (id);

        //先删除套餐关联的检查组
        setmealMapper.deleteBySetMealId (id);

        //再删除套餐信息
        setmealMapper.deleteById(id);

        //将删除后的套餐图片名称保存到缓存
        if (redisTemplate.opsForValue ().get (RedisConstant.IMG_SAVE)!=null){
            Set<String> set = (Set<String>) redisTemplate.opsForValue ().get (RedisConstant.IMG_SAVE);
            set.add (setmeal.getImg ());
            redisTemplate.opsForValue ().set (RedisConstant.IMG_SAVE,set);
        }
    }


}
