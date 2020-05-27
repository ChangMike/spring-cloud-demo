package com.my.usercon.sumer.controller;

import com.my.model.User;
import com.my.usercon.sumer.facade.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserConsumer {

    @Autowired
    private UserFeign userFeign;

    /**
     * 测试json：{"name":"zs","age":89}
     */
    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String hello(@RequestBody User user) {
        for (int i = 0; i < 30; i++) {
            userFeign.hello(user);
        }
        return "负载均衡测试";
    }
}
