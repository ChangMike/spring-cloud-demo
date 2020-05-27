package com.my.user.provider2.controller;

import com.my.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserProvider {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/provider/hello", method = RequestMethod.POST)
    public String hello(@RequestBody User user) {
        logger.info("访问 UserProvider 8881");
        return  user.getName() + "，你好！";
    }
}
