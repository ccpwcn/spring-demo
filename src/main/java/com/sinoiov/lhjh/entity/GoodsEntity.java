package com.sinoiov.lhjh.entity;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * 商品类
 * Created by lidawei on 2017/5/21.
 */
public class GoodsEntity implements Serializable {
    private static final long serialVersionUID = 1372079349926377152L;

    private String goodsId;
    private String name;
    private String manufacture;
    private String retailer;
    private long expiredTime;

    public GoodsEntity() {
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
