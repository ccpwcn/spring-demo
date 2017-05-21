package com.sinoiov.lhjh.service;

import com.sinoiov.lhjh.beans.GenericResponse;
import com.sinoiov.lhjh.entity.UserEntity;

/**
 * 用户管理功能服务层
 * Created by lidawei on 2017/5/21.
 */
public interface UserService {
    GenericResponse add(UserEntity entity);
    GenericResponse remove(UserEntity entity);
    GenericResponse update(UserEntity entity);
    GenericResponse<UserEntity> queryUserInfoById(String userId);
}
