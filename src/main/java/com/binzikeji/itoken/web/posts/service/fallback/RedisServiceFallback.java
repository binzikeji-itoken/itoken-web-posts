package com.binzikeji.itoken.web.posts.service.fallback;

import com.binzikeji.itoken.web.posts.service.RedisService;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author Bin
 * @Date 2019/4/16 15:45
 **/
@Component
public class RedisServiceFallback implements RedisService {

    @Override
    public String put(String key, String value, long seconds) {
        return null;
    }

    @Override
    public String get(String key) {
        return null;
    }
}
