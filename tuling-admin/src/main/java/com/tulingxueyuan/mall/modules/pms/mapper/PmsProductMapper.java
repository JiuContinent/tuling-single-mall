package com.tulingxueyuan.mall.modules.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mall.dto.ProductUpdateInitDTO;

/**
 * <p>
 * 商品信息 Mapper 接口
 * </p>
 *
 * @author XuShu
 * @since 2022-05-11
 */
public interface PmsProductMapper extends BaseMapper<PmsProduct> {

    ProductUpdateInitDTO getProductInfo(Long id);
}
