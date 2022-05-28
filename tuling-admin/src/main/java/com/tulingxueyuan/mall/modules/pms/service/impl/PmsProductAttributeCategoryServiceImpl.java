package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.dto.ProductAttributeCateDTO;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductAttributeCategoryMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeCategory;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2022-05-11
 */
@Service
@Transactional
public class PmsProductAttributeCategoryServiceImpl extends ServiceImpl<PmsProductAttributeCategoryMapper, PmsProductAttributeCategory> implements PmsProductAttributeCategoryService {
    @Autowired
    PmsProductAttributeCategoryMapper productAttributeCategoryMapper;
    @Override
    public Page list(Integer pageNum, Integer pageSize) {
       Page page = new Page(pageNum,pageSize);
        return this.page(page);
    }

    @Override
    public boolean add(PmsProductAttributeCategory productAttributeCategory) {
        productAttributeCategory.setAttributeCount(0);
        productAttributeCategory.setParamCount(0);
        boolean add = this.save(productAttributeCategory);
        return add;
    }

    /**
     * 筛选属性 级联数据
     * @return
     */
    @Override
    public List<ProductAttributeCateDTO> getListWithAttr() {
        return productAttributeCategoryMapper.getListWithAttr();
    }
}
