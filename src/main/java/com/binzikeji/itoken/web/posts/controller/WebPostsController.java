package com.binzikeji.itoken.web.posts.controller;

import com.binzikeji.itoken.common.domain.TbPostsPost;
import com.binzikeji.itoken.common.domain.TbSysUser;
import com.binzikeji.itoken.common.dto.BaseRestult;
import com.binzikeji.itoken.common.utils.MapperUtils;
import com.binzikeji.itoken.common.web.components.datatables.DataTablesResult;
import com.binzikeji.itoken.common.web.conatants.WebContants;
import com.binzikeji.itoken.common.web.controller.BaseController;
import com.binzikeji.itoken.web.posts.service.TbPostsPostService;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @Description
 * @Author Bin
 * @Date 2019/5/7 10:11
 **/
@Controller
public class WebPostsController extends BaseController<TbPostsPost, TbPostsPostService>{

    @Autowired
    private TbPostsPostService tbPostsPostService;

    @ModelAttribute
    public TbPostsPost tbPostsPost(String postGuid){
        TbPostsPost tbPostsPost = null;
        if (StringUtils.isBlank(postGuid)){
            tbPostsPost = new TbPostsPost();
        } else {
            String json = tbPostsPostService.get(postGuid);
            try {
                tbPostsPost = MapperUtils.json2pojo(json, TbPostsPost.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (tbPostsPost == null){
                tbPostsPost = new TbPostsPost();
            }
        }
        return tbPostsPost;
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = {"", "main"}, method = RequestMethod.GET)
    public String main(){
        return "main";
    }

    @RequestMapping(value = "from", method = RequestMethod.GET)
    public String from(){
        return "from";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public BaseRestult save(@RequestBody Map map, HttpServletRequest request) throws Exception {
        TbPostsPost tbPostsPost = new TbPostsPost();
        tbPostsPost.setTitle(map.get("title").toString());
        tbPostsPost.setTimePublished(new Date());
        tbPostsPost.setStatus("0");
        String tbPostsPostJson = MapperUtils.obj2json(tbPostsPost);
        TbSysUser tbSysUser = (TbSysUser) request.getSession().getAttribute(WebContants.SESSION_USER);
        String json = tbPostsPostService.save(tbPostsPostJson, tbSysUser.getUserCode());
        BaseRestult baseRestult = MapperUtils.json2pojo(json, BaseRestult.class);
        if (baseRestult.getSuccess().endsWith("成功")){
            return BaseRestult.ok("保存成功!", "/index");
        }
        return BaseRestult.ok("保存失败!", "/from");
    }
}
