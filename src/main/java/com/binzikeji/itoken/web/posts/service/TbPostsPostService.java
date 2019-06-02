package com.binzikeji.itoken.web.posts.service;

import com.binzikeji.itoken.common.web.service.BaseClientService;
import com.binzikeji.itoken.web.posts.service.fallback.TbPostsPostServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description
 * @Author Bin
 * @Date 2019/5/18 17:47
 **/
@Service
@FeignClient(value = "itoken-service-posts", fallback = TbPostsPostServiceFallback.class)
public interface TbPostsPostService extends BaseClientService {

    @RequestMapping(value = "/v1/posts/page/{pageNum}/{pageSize}", method = RequestMethod.GET)
    String page(
            @PathVariable(value = "pageNum") int pageNum,
            @PathVariable(value = "pageSize") int pageSize,
            @RequestParam(required = false, value = "tbPostsPostJson") String tbPostsPostJson);

    @RequestMapping(value = "/v1/posts/{postsGuid}", method = RequestMethod.GET)
    String get(@PathVariable(value = "postsGuid") String postsGuid);

    @RequestMapping(value = "/v1/posts/save", method = RequestMethod.POST)
    String save(
            @RequestParam(required = true, value = "tbPostsPostJson") String tbPostsPostJson,
            @RequestParam(required = true, value = "optsBy") String optsBy);
}
