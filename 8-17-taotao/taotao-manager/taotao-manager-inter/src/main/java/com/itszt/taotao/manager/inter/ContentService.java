package com.itszt.taotao.manager.inter;

import com.itszt.taotao.common.EasyUIAddContentBean;
import com.itszt.taotao.common.EasyUIContentBean;
import com.itszt.taotao.common.EasyUIPageDatasBean;
import com.itszt.taotao.common.EasyUITreeBean;
import com.itszt.taotao.manager.pojo.TbContent;

import java.util.List;

public interface ContentService {

    List<EasyUITreeBean> showContentTree(String parantID);

    EasyUIPageDatasBean<TbContent> showTbContentByID(int pageNumber, int pageSize, String id);

    EasyUIContentBean addContentBean(String parentId, String name );

    void updateContentBean(String id,String name);

    void deleteContentBean(String parentId, String id);

    EasyUIAddContentBean addTb_Content(TbContent tbContent);

    List<TbContent> getTb_ContentByID(String parentID) ;
}
