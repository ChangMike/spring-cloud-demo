package com.my.usercon.sumer.facade;

import com.my.model.User;
import com.my.usercon.sumer.UserFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="user-provider", fallback = UserFallback.class)
public interface UserFeign {
    // 下面这一行注解要和提供方一样
    @RequestMapping(value = "/provider/hello", method = RequestMethod.POST)
    String hello(User user);
}
