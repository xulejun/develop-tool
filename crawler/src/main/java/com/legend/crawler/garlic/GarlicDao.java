package com.legend.crawler.garlic;


/**
 * @author lejunxu
 */
public interface GarlicDao {

    int deleteByPrimaryKey(Integer id);

    int insert(Garlic record);

    int insertSelective(Garlic record);

    Garlic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Garlic record);

    int updateByPrimaryKey(Garlic record);

    int replace(Garlic garlic);
}




