package com.tulingxueyuan.mall.modules.pms.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mall.dto.ProductConditionDTO;
import com.tulingxueyuan.mall.dto.ProductSaveParamsDTO;
import com.tulingxueyuan.mall.dto.ProductUpdateInitDTO;

import java.util.List;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author XuShu
 * @since 2022-05-11
 */
public interface PmsProductService extends IService<PmsProduct> {

    Page getProductList(ProductConditionDTO conditionDTO);
    /**  跟新单个字段的公共方法
     * description: getProductStatus
     * version: 1.0
     * date: 2022/5/21 17:50
     * @author: objcat
     * @param status
     * @param ids
     * @param  getPublishStatus
     * @return boolean
     */
    boolean getProductStatus(Integer status, List<Long> ids, SFunction<PmsProduct,?> getPublishStatus);

    boolean createProduct(ProductSaveParamsDTO productSaveParamsDTO);

    ProductUpdateInitDTO getProductInfo(Long id);

    boolean updateProductInfo(ProductSaveParamsDTO productSaveParamsDTO);
}
