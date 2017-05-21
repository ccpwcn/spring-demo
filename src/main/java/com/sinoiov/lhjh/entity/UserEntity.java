package com.sinoiov.lhjh.entity;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * 用户类
 * Created by lidawei on 2017/5/21.
 */
public class UserEntity implements Serializable {
    private static final long serialVersionUID = -4923868344408115477L;
    private String userId;
    private int gender;
    private String nickName;
    private String phone;
    private int age;

    public UserEntity() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
