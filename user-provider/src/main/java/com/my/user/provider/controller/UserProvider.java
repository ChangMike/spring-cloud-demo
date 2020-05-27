package com.my.user.provider.controller;

import com.my.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserProvider {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/provider/hello", method = RequestMethod.POST)
    public String hello(@RequestBody User user) {
        logger.info("访问 UserProvider 8880");
        return  user.getName() + "，你好！";
    }
}
