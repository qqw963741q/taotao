package com.itszt.taotao.manager.dao;


import com.itszt.taotao.manager.pojo.TbItemCat;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.itszt.taotao.manager.pojo.TbItem;

import java.util.List;

@Repository
public interface GoodsItemDao {

    @Select("select * from tb_item")
    List<TbItem> queryTbItemAll();

    @Select("select * from tb_item_cat where parent_id=#{param}")
    List<TbItemCat> queryCatItems(String parentID);
}
