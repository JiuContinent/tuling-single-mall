package com.tulingxueyuan.mall.dto;

import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author JiuContinent
 * @create 2022/5/18 15:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ProductAttributeCateDTO筛选属性数据传输对象",description = "用于商品分类--筛选属性列表数据")
public class ProductAttributeCateDTO {
    //商品类型id
    private Long id;
    //商品类型名称
    private String name;
    //商品属性二级级联
    private List<PmsProductAttribute> productAttributeList;
}
