package com.binzikeji.itoken.web.posts.service.fallback;

import com.binzikeji.itoken.common.hystrix.Fallback;
import com.binzikeji.itoken.web.posts.service.TbPostsPostService;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author Bin
 * @Date 2019/5/18 18:07
 **/
@Component
public class TbPostsPostServiceFallback implements TbPostsPostService {

    @Override
    public String page(int pageNum, int pageSize, String tbPostsPostJson) {
        return Fallback.badGateway();
    }

    @Override
    public String get(String postsGuid) {
        return null;
    }

    @Override
    public String save(String tbPostsPostJson, String optsBy) {
        return Fallback.badGateway();
    }
}
