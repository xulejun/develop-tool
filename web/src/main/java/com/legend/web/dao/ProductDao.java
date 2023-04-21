package com.legend.web.dao;


import com.legend.web.bean.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xlj
 * @date 2021/4/29
 */
@Mapper
public interface ProductDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectAll();

    List<Product> selectSkillProduct();
}
