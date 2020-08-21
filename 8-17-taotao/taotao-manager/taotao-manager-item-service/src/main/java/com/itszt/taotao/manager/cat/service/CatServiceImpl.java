package com.itszt.taotao.manager.cat.service;

import com.itszt.taotao.common.EasyUITreeBean;
import com.itszt.taotao.manager.dao.GoodsItemDao;
import com.itszt.taotao.manager.inter.CatService;
import com.itszt.taotao.manager.pojo.TbItemCat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CatServiceImpl implements CatService {

    @Resource
    private GoodsItemDao goodsItemDao;

    @Override
    @Transactional()
    public List<EasyUITreeBean> showCatItems(String parentID)  {
        //查询指定parentID 的数据放入到集合中
        List<TbItemCat> tbItemCats = goodsItemDao.queryCatItems(parentID);

        List<EasyUITreeBean> easyUITreeBeans = new ArrayList<>();

        //说明有数据
        if (tbItemCats != null && tbItemCats.size() > 0) {
            for (TbItemCat tbItemCat : tbItemCats) {
                EasyUITreeBean easyUITreeBean=new EasyUITreeBean();

                //遍历出来的每个对象指定数据储存到业务Bean
                easyUITreeBean.setText(tbItemCat.getName());
                easyUITreeBean.setId(tbItemCat.getId());
                //控制是否为父权限,  如果是,可以点击,  如果不是, 则关闭点击按钮
                if (tbItemCat.getIsParent()) {
                    easyUITreeBean.setState(EasyUITreeBean.STATE_CLOSED);
                }else {
                    easyUITreeBean.setState(EasyUITreeBean.STATE_OPEN );
                }
                easyUITreeBeans.add(easyUITreeBean);
            }
        }

        return easyUITreeBeans;
    }
}
