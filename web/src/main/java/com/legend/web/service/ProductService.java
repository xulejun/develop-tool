package com.legend.web.service;


import com.legend.common.entity.Product;

import java.util.List;

/**
 * @author xlj
 * @date 2021/4/29
 */
public interface ProductService {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectAll();

    List<Product> selectSkillProduct();
}
