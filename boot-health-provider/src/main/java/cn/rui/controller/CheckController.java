package cn.rui.controller;

import cn.rui.pojo.CheckItem;
import cn.rui.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 徽州大都督
 * @date 2020/8/23
 */
@RestController
@RequestMapping("check")
public class CheckController {

    @Autowired
    private CheckItemService checkItemService;

    @RequestMapping("add")
    public void add(){

        CheckItem checkItem = new CheckItem ();
        checkItem.setType ("1");
        checkItem.setSex ("1");
        checkItem.setRemark ("123");
        checkItem.setPrice ((float) 13);
        checkItem.setName ("123");
        checkItem.setId (null);
        checkItem.setCode ("123");
        checkItem.setAttention ("123");
        checkItem.setAge ("123");

        checkItemService.add (checkItem);
    }
}
