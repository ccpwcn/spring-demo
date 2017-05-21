package com.sinoiov.lhjh.controller;

import com.sinoiov.lhjh.beans.GenericResponse;
import com.sinoiov.lhjh.entity.UserEntity;
import com.sinoiov.lhjh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody
    GenericResponse add(@RequestBody UserEntity entity) {
        return userService.add(entity);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody
    GenericResponse remove(@RequestBody UserEntity entity) {
        return userService.remove(entity);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody
    GenericResponse update(@RequestBody UserEntity entity) {
        return userService.update(entity);
    }

    @RequestMapping(value = "/queryUserInfoById", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody
    GenericResponse<UserEntity> queryUserInfoById(String userId) {
        return userService.queryUserInfoById(userId);
    }
}
