package com.sinoiov.lhjh.controller;

import com.sinoiov.lhjh.beans.GenericResponse;
import com.sinoiov.lhjh.entity.UserEntity;
import com.sinoiov.lhjh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户相关接口
 * Created by lidawei on 2017/5/21.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add")
    public @ResponseBody
    GenericResponse add(UserEntity entity) {
        GenericResponse response = new GenericResponse();
        userService.add(entity);
        return response;
    }

    @RequestMapping(value = "/remove")
    public @ResponseBody
    GenericResponse remove(UserEntity entity) {
        GenericResponse response = new GenericResponse();
        userService.remove(entity);
        return response;
    }

    @RequestMapping(value = "/update")
    public @ResponseBody
    GenericResponse update(UserEntity entity) {
        GenericResponse response = new GenericResponse();
        userService.update(entity);
        return response;
    }

    @RequestMapping(value = "/queryUserInfoById")
    public @ResponseBody
    GenericResponse<UserEntity> queryUserInfoById(String userId) {
        GenericResponse response = new GenericResponse();
        userService.queryUserInfoById(userId);
        return response;
    }
}
