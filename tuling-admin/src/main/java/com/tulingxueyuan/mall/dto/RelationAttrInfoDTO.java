package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author JiuContinent
 * @create 2022/5/18 21:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ProductAttributeCateDTO 分类和筛选属性管理数数据",description = "用于筛选属性的已保存数据")
public class RelationAttrInfoDTO {
    /**
     *     商品类型id
     */
    private Long attributeCategoryId;
    /**
      *商品属性id
     */
    private Long attributeId;
}
