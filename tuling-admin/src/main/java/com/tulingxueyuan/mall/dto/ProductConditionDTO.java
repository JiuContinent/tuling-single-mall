package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author JiuContinent
 * @create 2022/5/19 23:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ProductConditionDTO商品列表筛选条件",description = "用于商品列表筛选条件参数接收")
public class ProductConditionDTO {
    private String keyword;
    @ApiModelProperty(value = "页数量")
    private Integer pageNum;
    @ApiModelProperty(value = "页大小")
    private Integer pageSize;
    @ApiModelProperty(value = "上架状态：0-未上架 1 - 已上架")
    private Integer publishStatus;
    @ApiModelProperty(value = "审核状态：0-未审核 1 - 审核通过 ")
    private Integer verifyStatus;
    @ApiModelProperty(value = "货号")
    private String productSn;
    @ApiModelProperty(value = "商品id")
    private Long productCategoryId;
    @ApiModelProperty(value = "品牌id")
    private Long brandId;
}
