package com.itszt.taotao.manager.controller;

import com.itszt.taotao.common.EasyUIAddContentBean;
import com.itszt.taotao.common.EasyUIContentBean;
import com.itszt.taotao.common.EasyUIPageDatasBean;
import com.itszt.taotao.common.EasyUITreeBean;
import com.itszt.taotao.manager.inter.ContentService;
import com.itszt.taotao.manager.pojo.TbContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Predicate;

@Controller
public class ContentController {


    @Resource
    private ContentService contentService;

    @Value("${FDFS_URL}")
    private String FDFS_URL;

    //对于侧边栏tree的显示
    @GetMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeBean> getContentTree(@RequestParam(required = false, defaultValue = "0") String id) {
        System.out.println("id = " + id);
        List<EasyUITreeBean> easyUITreeBeans = contentService.showContentTree(id);
        return easyUITreeBeans;
    }
    //对于tree点击后,显示的数据
    @GetMapping("/content/query/list")
    @ResponseBody
    public EasyUIPageDatasBean itemList(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(required = false, defaultValue = "30") String rows, String categoryId) {
        System.out.println("page = " + page + ", rows = " + rows + ", id = " + categoryId);
        //对于指定标签的 数据显示
        EasyUIPageDatasBean<TbContent> tbContentBean = contentService.showTbContentByID(Integer.parseInt(page), Integer.parseInt(rows), categoryId);
        List<TbContent> tbContents = tbContentBean.getRows();
        for (TbContent tbContent : tbContents) {
            tbContent.setPic(FDFS_URL+tbContent.getPic());
            tbContent.setPic2(FDFS_URL+tbContent.getPic2());
        }
        return tbContentBean;
    }
    //添加新的节点
    @PostMapping("/content/category/create")
    @ResponseBody
    public EasyUIContentBean addContentBean(String parentId, String name) {
        System.out.println("添加数据的controller" + "parentId = " + parentId + ", name = " + name);
        EasyUIContentBean easyUIContentBean = contentService.addContentBean(parentId, name);
        //新增之前做校验, 不能新增相同的name, 并且 如果该name对应其他表有内容, 则也不能新增
        //数据添加时候,需要返回结果信息
        return easyUIContentBean;

    }

    //修改一个指定id的节点
    @PostMapping("/content/category/update")
    public void updateContentBean(String id, String name) {
        System.out.println("修改数据的controller" + "id = " + id + ", name = " + name);
    }

    @PostMapping("/content/category/delete/")
    public void deleteContentBean(String parentId, String id) {
        System.out.println("删除数据的controller" + "parentId = " + parentId + ", id = " + id);
    }


    @PostMapping("/content/save")
    @ResponseBody
    public EasyUIAddContentBean addTb_Content(TbContent tbContent) {
        System.out.println("tbContent = " + tbContent);
        tbContent.setPic(tbContent.getPic().replace(FDFS_URL,""));
        tbContent.setPic2( tbContent.getPic2().replace(FDFS_URL,""));
        return contentService.addTb_Content(tbContent);
    }



}
