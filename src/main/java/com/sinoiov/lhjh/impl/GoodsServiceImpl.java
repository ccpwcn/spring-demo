package com.sinoiov.lhjh.impl;

import com.sinoiov.lhjh.beans.GenericResponse;
import com.sinoiov.lhjh.entity.GoodsEntity;
import com.sinoiov.lhjh.service.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品管理服务实现类
 * Created by lidawei on 2017/5/21.
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    private static final Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);

    @Override
    public GenericResponse add(GoodsEntity entity) {
        logger.info("创建的值：{}", entity);
        return null;
    }

    @Override
    public GenericResponse remove(GoodsEntity entity) {
        return null;
    }

    @Override
    public GenericResponse update(GoodsEntity entity) {
        logger.info("更新的值：{}", entity);
        return null;
    }

    @Override
    public GenericResponse<List<GoodsEntity>> queryByName(String goodsName) {
        logger.info("查询：{}", goodsName);
        return null;
    }
}
