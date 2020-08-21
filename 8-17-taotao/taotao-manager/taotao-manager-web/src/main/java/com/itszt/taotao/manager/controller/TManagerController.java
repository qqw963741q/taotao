package com.itszt.taotao.manager.controller;

import com.itszt.taotao.common.EasyUIPageDatasBean;
import com.itszt.taotao.common.EasyUIPicUploadBean;
import com.itszt.taotao.common.EasyUITreeBean;
import com.itszt.taotao.common.FastDFSClientUtil;
import com.itszt.taotao.manager.inter.CatService;
import com.itszt.taotao.manager.inter.GoodsItemService;
import com.itszt.taotao.manager.pojo.TbItem;
import com.itszt.taotao.manager.pojo.TbItemCat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class TManagerController {

    //读取配置文件数据(.properties)
    @Value("${FDFS_URL}")
    private String FDFS_URL;
    @Resource
    private GoodsItemService goodsItemService;

    @Resource
    private CatService catService;
        //转发
    @RequestMapping("/{path}")
    public String GoToItem(@PathVariable String path) {
        System.out.println("path = " + path);
        return path;
    }

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIPageDatasBean getTbItemAll(@RequestParam(required = false,defaultValue = "1") String page,
                                            @RequestParam(required = false,defaultValue = "30") String rows){

        System.out.println("page = " + page + ", rows = " + rows);
        EasyUIPageDatasBean<TbItem> item = goodsItemService.getItems(page, rows);
        return item;
    }

    @RequestMapping("/pic/upload")
    @ResponseBody
    public EasyUIPicUploadBean uploadPics(MultipartFile uploadFile){
        //创建业务Bean
        EasyUIPicUploadBean easyUIPicUploadBean=new EasyUIPicUploadBean();
        try {

            //获取当前文件域的名称
            String originalFilename = uploadFile.getOriginalFilename();

            System.out.println("originalFilename = " + originalFilename);
            String expName="";
            //查看前端传递来的文件域是否有.
            if (originalFilename.contains(".")){
                //如果有则进行拆分,  获取到角标1的后缀, 赋值给expName
                expName=originalFilename.split("\\.")[1];
            }

            //将文件域的数据转换成byte数组
            byte[] picDatas=uploadFile.getBytes();

            //创建FastDFS工具类对象
            FastDFSClientUtil fastDFSClientUtil=new FastDFSClientUtil();


            //返回ip+port+储存在文件服务器的路径
            String fdfsPath = FDFS_URL+fastDFSClientUtil.uploadFile(picDatas, expName);

            System.out.println("fdfsPath = " + fdfsPath);

            easyUIPicUploadBean.setError(0);
            easyUIPicUploadBean.setMessage("上传成功");
            easyUIPicUploadBean.setUrl(fdfsPath);
        } catch (Exception e) {
            e.printStackTrace();

            easyUIPicUploadBean.setError(1);
            easyUIPicUploadBean.setMessage("上传失败，请稍后重试！");
        }
        return easyUIPicUploadBean;
    }

    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeBean> getItemCat(@RequestParam(required = false,defaultValue = "0") String id){
        System.out.println("id = " + id);

        List<EasyUITreeBean> easyUITreeBeans = catService.showCatItems(id);

        return easyUITreeBeans;
    }
}
