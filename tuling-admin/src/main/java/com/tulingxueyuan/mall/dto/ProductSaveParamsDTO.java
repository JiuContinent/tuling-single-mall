package com.tulingxueyuan.mall.dto;

import com.tulingxueyuan.mall.modules.pms.model.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Author JiuContinent
 * @create 2022/5/21 20:35
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ProductSaveParamsDTO 商品保存数据传输对象",description = "用于商品的添加、修改保存的参数接收")
public class ProductSaveParamsDTO extends PmsProduct {
    /**
     *     会员价格
     */
    @ApiModelProperty(value = "会员价格")
    private List<PmsMemberPrice> memberPriceList;
    /**
     *     商品满减
     */
    @ApiModelProperty(value = "商品满减")
    private List<PmsProductFullReduction> productFullReductionList;
    /**
     *  商品阶梯价格
     */
    @ApiModelProperty("商品阶梯价格")
    private List<PmsProductLadder> productLadderList;
    /**
     * 商品属性相关
     */
    @ApiModelProperty("商品属性相关")
    private List<PmsProductAttributeValue> productAttributeValueList;
    /**
     *  商品SKU库存信息
     *  @Valid 嵌套支持验证
     */
    @ApiModelProperty("商品SKU库存信息")
    @Size(min = 1,message = "SKU至少要有一项")
    @Valid
    private List<PmsSkuStock> skuStockList;
}
