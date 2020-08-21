package com.itszt.taotao.manager.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itszt.taotao.common.EasyUIAddContentBean;
import com.itszt.taotao.common.EasyUIContentBean;
import com.itszt.taotao.common.EasyUIPageDatasBean;
import com.itszt.taotao.common.EasyUITreeBean;
import com.itszt.taotao.manager.dao.ContentDao;
import com.itszt.taotao.manager.inter.ContentService;
import com.itszt.taotao.manager.pojo.TbContent;
import com.itszt.taotao.manager.pojo.TbContentCategory;
import org.springframework.data.redis.core.ClusterOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Resource
    private ContentDao contentDao;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public List<EasyUITreeBean> showContentTree(String parantID) {

        List<TbContentCategory> tbContentCategories = contentDao.queryContentTree(Long.parseLong(parantID));

        List<EasyUITreeBean> easyUITreeBeans = new ArrayList<>();

        if (tbContentCategories != null && tbContentCategories.size() > 0) {


            for (TbContentCategory tbContentCategory : tbContentCategories) {
                EasyUITreeBean easyUITreeBean = new EasyUITreeBean();

                easyUITreeBean.setId(tbContentCategory.getId());
                easyUITreeBean.setText(tbContentCategory.getName());

                if (tbContentCategory.getIsParent()) {
                    easyUITreeBean.setState(EasyUITreeBean.STATE_CLOSED);
                } else {
                    easyUITreeBean.setState(EasyUITreeBean.STATE_OPEN);
                }
                easyUITreeBeans.add(easyUITreeBean);
            }
        }

        return easyUITreeBeans;
    }

    @Override
    public EasyUIPageDatasBean<TbContent> showTbContentByID(int pageNumber, int pageSize, String id) {

        PageHelper.startPage(pageNumber, pageSize);

        List<TbContent> tbContents = contentDao.queryTbContentByID(Long.parseLong(id));

        PageInfo<TbContent> pageInfo = new PageInfo(tbContents);

        EasyUIPageDatasBean<TbContent> easyUIPageDatasBean = new EasyUIPageDatasBean();

        easyUIPageDatasBean.setTotal(pageInfo.getTotal());
        easyUIPageDatasBean.setRows(tbContents);
        return easyUIPageDatasBean;
    }


    @Override
    public EasyUIContentBean addContentBean(String parentId, String name) {
        //数据的校验>parentID不用校验, 因为该值必定不为null

        //对于name的查重,又则直接抛出异常

        //对于parent_dID 中is_parent属性是否为1(父节点)的判断

        //对于节点的关联表查询, 是否有关联,如果有,则不能为父节点,没有则改变该节点状态为1,


        EasyUIContentBean easyUIContentBean = new EasyUIContentBean();
        if (name == null && "".equals(name.trim())) {
            easyUIContentBean.setStatus(EasyUIContentBean.NOK);
        }


        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setUpdated(new Date());
        tbContentCategory.setName(name);
        tbContentCategory.setIsParent(false);
        tbContentCategory.setParentId(Long.parseLong(parentId));

        try {
            contentDao.insertCategory(tbContentCategory);
            easyUIContentBean.setStatus(EasyUIContentBean.OK);
            easyUIContentBean.setId(parentId);
            easyUIContentBean.setTarget(name);
        } catch (Exception e) {
            e.printStackTrace();
            easyUIContentBean.setStatus(EasyUIContentBean.NOK);
            easyUIContentBean.setTarget(name);
        }
        return easyUIContentBean;
    }


    @Override
    public void updateContentBean(String id, String name) {
    }

    @Override
    public void deleteContentBean(String parentId, String id) {

    }

    @Override
    public EasyUIAddContentBean addTb_Content(TbContent tbContent) {


        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        EasyUIAddContentBean easyUIAddContentBean = new EasyUIAddContentBean();


        try {
            contentDao.insertTb_Content(tbContent);


            easyUIAddContentBean.setStatus(EasyUIAddContentBean.OK);
            easyUIAddContentBean.setMessager("新增" + tbContent.getTitle() + "成功");

            redisTemplate.delete("TbContent"+tbContent.getCategoryId());
            System.out.println("addTb_Content--->"+"redis的删除操作");
        } catch (Exception e) {
            easyUIAddContentBean.setStatus(EasyUIAddContentBean.NOK);
            easyUIAddContentBean.setMessager("新增" + tbContent.getTitle() + "失败,请重试");
            e.printStackTrace();
        }
        return easyUIAddContentBean;
    }



    @Override
    public List<TbContent> getTb_ContentByID(String parentID) {
        String str = "TbContent" + parentID;
        List<TbContent> tbContents = null;
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object value = valueOperations.get(str);
        if (value == null) {
            System.out.println("缓存中没有, 去数据库查询");
            //缓存中没有, 去数据库查询
            tbContents = contentDao.queryTbContentByID(Long.parseLong(parentID));
            String jsonString = JSON.toJSONString(tbContents);
            //将数据储存到redis数据库
            valueOperations.set(str, jsonString);
        } else {
            System.out.println("缓存中有, 直接从缓存中获取");
            //缓存中有, 返回结果
            tbContents = JSON.parseArray((String) value, TbContent.class);
        }
        return tbContents;
    }


}
