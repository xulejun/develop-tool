package com.legend.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品
 *
 * @author xlj
 * @date 2021/4/29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Integer id;
    /**
     * 商品名
     */
    private String name;
    /**
     * 商品价格
     */
    private Double price;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 秒杀随机码
     */
    private String skillCode;
    /**
     * 创建时间
     */
    private String createTime;
}
