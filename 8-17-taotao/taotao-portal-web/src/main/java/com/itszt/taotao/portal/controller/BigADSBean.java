package com.itszt.taotao.portal.controller;

import com.itszt.taotao.manager.pojo.TbContent;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: taotao-parent
 * @description: 对应前端key的Bean(后台 : Tbcontent)
 * @author: SmileQ
 * @create: 2020-08-20 10:50
 **/
public class BigADSBean {
    private String src;
    private Integer height;
    private Integer width;
    private String srcB;
    private Integer heightB;
    private Integer widthB;
    private String alt;
    private String href;


    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getSrcB() {
        return srcB;
    }

    public void setSrcB(String srcB) {
        this.srcB = srcB;
    }

    public Integer getHeightB() {
        return heightB;
    }

    public void setHeightB(Integer heightB) {
        this.heightB = heightB;
    }

    public Integer getWidthB() {
        return widthB;
    }

    public void setWidthB(Integer widthB) {
        this.widthB = widthB;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public static List<BigADSBean> setBigADSBean(List<TbContent> tbContents, Integer srcHeight, Integer srcWidth, String fadfs_url) {
        List<BigADSBean> bigADSBeans = new ArrayList<>();

        if (tbContents == null || tbContents.size() <= 0) {
            return bigADSBeans;
        }
        for (TbContent tbContent : tbContents) {
            BigADSBean bigADSBean = new BigADSBean();
            bigADSBean.setSrc(fadfs_url + tbContent.getPic());
            bigADSBean.setSrcB(fadfs_url + tbContent.getPic2());
            bigADSBean.setHeight(srcHeight);
            bigADSBean.setHeightB(srcHeight);
            bigADSBean.setWidth(srcWidth);
            bigADSBean.setWidthB(srcWidth);
            bigADSBean.setAlt(tbContent.getTitle());
            bigADSBean.setHref(tbContent.getUrl());
            bigADSBeans.add(bigADSBean);
        }

        return bigADSBeans;
    }
}
