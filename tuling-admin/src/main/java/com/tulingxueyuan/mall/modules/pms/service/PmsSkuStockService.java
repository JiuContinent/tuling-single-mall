package com.tulingxueyuan.mall.modules.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.modules.pms.model.PmsSkuStock;

import java.util.List;

/**
 * <p>
 * sku的库存 服务类
 * </p>
 *
 * @author XuShu
 * @since 2022-05-11
 */
public interface PmsSkuStockService extends IService<PmsSkuStock> {

    List<PmsSkuStock> list(Long id,String keyword);

    Boolean skuStockUpdate(List<PmsSkuStock> pmsSkuStocks);
}
