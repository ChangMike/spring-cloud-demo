package com.my.usercon.sumer;

import com.my.model.User;
import com.my.usercon.sumer.facade.UserFeign;
import org.springframework.stereotype.Component;

@Component
public class UserFallback implements UserFeign {

    public String hello(User user) {
        return "自定义错误";
    }
}
