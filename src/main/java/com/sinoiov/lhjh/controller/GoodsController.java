package com.sinoiov.lhjh.controller;

import com.sinoiov.lhjh.beans.GenericResponse;
import com.sinoiov.lhjh.entity.GoodsEntity;
import com.sinoiov.lhjh.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品类前端接口控制器
 * Created by lidawei on 2017/5/21.
 */
@Controller
@RequestMapping(value = "/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody
    GenericResponse add(@RequestBody GoodsEntity entity) {
        return goodsService.add(entity);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public @ResponseBody
    GenericResponse remove(@RequestBody GoodsEntity entity) {
        return goodsService.remove(entity);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody
    GenericResponse update(@RequestBody GoodsEntity entity) {
        return goodsService.update(entity);
    }

    @RequestMapping(value = "/queryByName", method = RequestMethod.POST)
    public @ResponseBody
    GenericResponse queryByName(String goodsName) {
        return goodsService.queryByName(goodsName);
    }
}
