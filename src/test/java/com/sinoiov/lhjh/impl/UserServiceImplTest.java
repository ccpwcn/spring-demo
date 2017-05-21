package com.sinoiov.lhjh.impl;

import com.sinoiov.lhjh.entity.UserEntity;
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
public class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    public void add() {
        UserEntity entity = new UserEntity();
        entity.setUserId(UUID.randomUUID().toString());
        entity.setNickName("HelloWorld");
        entity.setGender(1);
        entity.setPhone("13012345678");
        entity.setAge(30);
        userService.add(entity);
    }
}
