package com.itszt.taotao.portal.controller;

import com.itszt.taotao.manager.inter.ContentService;
import com.itszt.taotao.manager.pojo.TbContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class portIndexController {

    @Value("${ADVERTISING}")
    private String ID;

    @Value("${FDFS_URL}")
    private String FDFS_URL;

    @Value("${SRC_WIDTH}")
    private Integer SRC_WIDTH;

    @Value("${SRC_HEIGHT}")
    private Integer SRC_HEIGHT;


    @Resource
    private ContentService contentService;


    @PostMapping("/getBigADS.json")
    @ResponseBody
    public List<BigADSBean> getBigADS(){

        //去数据库查询大广告的数据
        List<TbContent> tb_contentByID = contentService.getTb_ContentByID(ID);

        List<BigADSBean> bigADSBeans = BigADSBean.setBigADSBean(tb_contentByID, SRC_HEIGHT, SRC_WIDTH, FDFS_URL);

        return bigADSBeans;

    }
}
