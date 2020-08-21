package com.itszt.taotao.manager.inter;


import com.itszt.taotao.common.EasyUITreeBean;

import java.util.List;

public interface CatService {

    List<EasyUITreeBean> showCatItems(String parentID);
}
