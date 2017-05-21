package com.sinoiov.lhjh.impl;

import com.sinoiov.lhjh.entity.GoodsEntity;
import com.sinoiov.lhjh.entity.UserEntity;
import com.sinoiov.lhjh.service.GoodsService;
import com.sinoiov.lhjh.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.UUID;

/**
 * 用户服务实现类测试用例
 * Created by lidawei on 2017/5/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mvc.xml", "classpath:knights.xml"})
@WebAppConfiguration
public class GoodsServiceImplTest {
    @Autowired
    private GoodsService goodsService;

    @Test
    public void add() {
        GoodsEntity entity = new GoodsEntity();
        entity.setGoodsId(UUID.randomUUID().toString());
        entity.setName("MyGoods");
        entity.setManufacture("Baidu");
        entity.setManufacture("Huawei");
        entity.setRetailer("wumei");
        goodsService.add(entity);
    }
}
