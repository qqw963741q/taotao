package com.itszt.taotao.manager.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.itszt.taotao.common.EasyUIPageDatasBean;
import com.itszt.taotao.manager.dao.GoodsItemDao;
import com.itszt.taotao.manager.inter.GoodsItemService;
import org.springframework.stereotype.Service;
import com.itszt.taotao.manager.pojo.TbItem;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsItemServiceImpl implements GoodsItemService {


    @Resource
    private GoodsItemDao goodsItemDao;

    @Override
    public EasyUIPageDatasBean<TbItem> getItems(String page, String rows) {
        //对数据进行校验


        //数据转换
        int  number= Integer.parseInt(page);
        int  size= Integer.parseInt(rows);

        //分页框架,参数1: 查那一页数据,  参数2:查询多少条
        PageHelper.startPage(number, size);

        //从那个表查哪些字段
        List<TbItem> tbItems = goodsItemDao.queryTbItemAll();

        //把查询回来的数据再封装, 该对象中存在所有需要的数据,
        PageInfo<TbItem> pageInfo = new PageInfo(tbItems);

        System.out.println(pageInfo.getList().size()+"---->我是现实的数据条数");

        EasyUIPageDatasBean<TbItem> easyUIPageDatasBean = new EasyUIPageDatasBean();
        easyUIPageDatasBean.setRows(tbItems);
        easyUIPageDatasBean.setTotal(pageInfo.getTotal());
        return easyUIPageDatasBean;

    }
}

