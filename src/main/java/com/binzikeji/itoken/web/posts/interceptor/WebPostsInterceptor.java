package com.binzikeji.itoken.web.posts.interceptor;

import com.binzikeji.itoken.common.domain.TbSysUser;
import com.binzikeji.itoken.common.utils.CookieUtils;
import com.binzikeji.itoken.common.utils.MapperUtils;

import com.binzikeji.itoken.common.web.conatants.WebContants;
import com.binzikeji.itoken.common.web.utils.HttpServletUtils;
import com.binzikeji.itoken.web.posts.service.RedisService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Description
 * @Author Bin
 * @Date 2019/4/17 16:08
 **/
public class WebPostsInterceptor implements HandlerInterceptor {

    @Value(value = "${hosts.sso}")
    private String hosts_sso;

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        String token = CookieUtils.getCookieValue(request, WebContants.SESSION_TOKEN);
        // token 为空一定没登了
        if (StringUtils.isBlank(token)){
            // http://localhost:8503/login?url=http://localhost:8602
            response.sendRedirect(String.format("%s/login?url=http://localhost:8602/%s", hosts_sso, request.getServletPath()));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession();
        TbSysUser tbSysUser = (TbSysUser) session.getAttribute(WebContants.SESSION_USER);

        // 已登录状态
        if (tbSysUser != null){
            if (modelAndView != null){
                modelAndView.addObject(WebContants.SESSION_USER, tbSysUser);
            }
        }

        // 未登录状态
        else {
            String token = CookieUtils.getCookieValue(request, WebContants.SESSION_TOKEN);
            if (StringUtils.isNotBlank(token)){
                String loginCode = redisService.get(token);
                if (StringUtils.isNotBlank(loginCode)){
                    String json = redisService.get(loginCode);
                    if (StringUtils.isNotBlank(json)){
                        // 已登录状态
                        tbSysUser = MapperUtils.json2pojo(json, TbSysUser.class);
                        if (modelAndView != null){
                            modelAndView.addObject(WebContants.SESSION_USER, tbSysUser);
                        }
                        request.getSession().setAttribute(WebContants.SESSION_USER, tbSysUser);
                    }
                }
            }
        }

        // 二次确认是否有用户信息
        if (tbSysUser == null){
            response.sendRedirect(String.format("%s/login?url=http://localhost:8602/%s", hosts_sso, request.getServletPath()));
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
