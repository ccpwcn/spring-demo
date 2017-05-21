package com.sinoiov.lhjh.controller;

import com.sinoiov.lhjh.beans.GenericResponse;
import com.sinoiov.lhjh.entity.GoodsEntity;
import com.sinoiov.lhjh.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping(value = "/add")
    public @ResponseBody
    GenericResponse add(@RequestBody GoodsEntity entity) {
        GenericResponse response = new GenericResponse();
        goodsService.add(entity);
        return response;
    }

    @RequestMapping(value = "/remove")
    public @ResponseBody
    GenericResponse remove(@RequestBody GoodsEntity entity) {
        GenericResponse response = new GenericResponse();
        goodsService.remove(entity);
        return response;
    }

    @RequestMapping(value = "/update")
    public @ResponseBody
    GenericResponse update(@RequestBody GoodsEntity entity) {
        GenericResponse response = new GenericResponse();
        goodsService.update(entity);
        return response;
    }

    @RequestMapping(value = "/queryByName")
    public @ResponseBody
    GenericResponse queryByName(String goodsName) {
        GenericResponse response = new GenericResponse();
        goodsService.queryByName(goodsName);
        return response;
    }
}
