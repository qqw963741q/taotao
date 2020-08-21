package com.itszt.taotao.manager.inter;


import com.itszt.taotao.common.EasyUIPageDatasBean;
import com.itszt.taotao.manager.pojo.TbItem;

public interface GoodsItemService {
    
     EasyUIPageDatasBean<TbItem> getItems(String pageSize, String pageNumber);
}
