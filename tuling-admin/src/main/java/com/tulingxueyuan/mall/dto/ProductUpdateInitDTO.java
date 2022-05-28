package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author JiuContinent
 * @create 2022/5/21 22:41
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ProductUpdateInitDTO修改的商品初始化数据",description = "由于商品修改数据初始化")
public class ProductUpdateInitDTO extends ProductSaveParamsDTO{
    /**
     *     一级分类id
     */
    private Long cateParentId;
}
