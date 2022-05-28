package com.tulingxueyuan.mall.modules.pms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.modules.pms.model.PmsBrand;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author XuShu
 * @since 2022-05-11
 */
public interface PmsBrandService extends IService<PmsBrand> {

    Page getBrandList(String keyword, Integer pageNum, Integer pageSize);
}
