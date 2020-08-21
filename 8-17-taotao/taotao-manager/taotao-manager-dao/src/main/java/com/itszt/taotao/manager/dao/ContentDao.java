package com.itszt.taotao.manager.dao;

import com.itszt.taotao.manager.pojo.TbContent;
import com.itszt.taotao.manager.pojo.TbContentCategory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentDao {

    @Select("select * from tb_content_category where parent_id=#{param}")
    List<TbContentCategory> queryContentTree(long parentID);

    @Select("select * from tb_content where category_id=#{param} ")
    List<TbContent> queryTbContentByID(long id);

    @Insert("insert into tb_content_category values(null,#{parentId},#{name},#{status},#{sortOrder},#{isParent},#{created},#{updated})")
    void insertCategory(TbContentCategory tbContentCategory);

    @Insert("insert into tb_content values(null,#{categoryId}," +
            "#{title}," +
            "#{subTitle}," +
            "#{titleDesc}," +
            "#{url}," +
            "#{pic}," +
            "#{pic2}," +
            "#{content}," +
            "#{created}," +
            "#{updated})")
    void insertTb_Content(TbContent tbContent);
}
