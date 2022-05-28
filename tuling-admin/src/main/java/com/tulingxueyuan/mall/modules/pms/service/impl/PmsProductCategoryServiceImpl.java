package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductCategoryMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategoryAttributeRelation;
import com.tulingxueyuan.mall.dto.PmsProductCategoryDTO;
import com.tulingxueyuan.mall.dto.ProductCateChildrenDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductCategoryAttributeRelationService;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2022-05-11
 */
@Transactional
@Service
public class PmsProductCategoryServiceImpl extends ServiceImpl<PmsProductCategoryMapper, PmsProductCategory> implements PmsProductCategoryService {

    @Autowired
    private PmsProductCategoryAttributeRelationService relationService;
    @Autowired
    private PmsProductCategoryMapper productCategoryMapper;

    @Override
    public Page list(Long parentId, Integer pageNum, Integer pageSize) {
        Page<PmsProductCategory> page = new Page(pageNum, pageSize);
        //条件构造器
        QueryWrapper<PmsProductCategory> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(PmsProductCategory::getParentId, parentId)
                .orderByAsc(PmsProductCategory::getSort);
        return this.page(page, queryWrapper);
    }

    /**
     * 修改导航栏显示状态
     *
     * @param ids
     * @param navStatus
     * @return
     */

    @Override
    public boolean updateNavStatus(List<Long> ids, Integer navStatus) {
        UpdateWrapper<PmsProductCategory> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                //需要跟新的列
                .set(PmsProductCategory::getNavStatus, navStatus)
                //条件
                .in(PmsProductCategory::getId, ids);
        return this.update(updateWrapper);
    }

    /**
     * 修改是否显示
     *
     * @param ids
     * @param showStatus
     * @return
     */
    @Override
    public boolean updateShowStatus(List<Long> ids, Integer showStatus) {

        UpdateWrapper<PmsProductCategory> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                //需要跟新的列
                .set(PmsProductCategory::getShowStatus, showStatus)
                //条件
                .in(PmsProductCategory::getId, ids);
        return this.update(updateWrapper);
    }

    @Override
    public boolean CustomSave(PmsProductCategoryDTO productCategoryDTO) {
        //保存商品分类
        PmsProductCategory productCategory = new PmsProductCategory();
        //通过BeanUtils 将productCategoryDTO 数据拷贝到productCateGory
        //为什么要拷贝 应为一定要通过this.save 去保存PmsProductCategory 因为只有它才能映射数据
        BeanUtils.copyProperties(productCategoryDTO,productCategory);
//        BeanUtils.copyProperties(productCategoryDTO, productCategory)
//        由于商品数量 和级别 在表单中没有维护 需要设置默认值
        productCategory.setProductCount(0);
        if (productCategory.getParentId() == 0) {
            //没有默认值就为0
            productCategory.setLevel(0);
        } else {
            //如果有多级分类 根据parentId查出商品分类获取level+1
            //由于只有2级分类，直接设置为1
            productCategory.setLevel(1);
        }
        //保存
        this.save(productCategory);
        saveAttrRelation(productCategoryDTO, productCategory);
        return true;
    }

    /**
     * 添加关联属性
     * @param productCategoryDTO
     * @param productCategory
     * @return
     */
    private boolean saveAttrRelation(PmsProductCategoryDTO productCategoryDTO, PmsProductCategory productCategory) {
        //商品筛选属性
        List<Long> productAttributeIdList = productCategoryDTO.getProductAttributeIdList();
        List<PmsProductCategoryAttributeRelation> list = new ArrayList<>();
        for (Long arrayId : productAttributeIdList) {
            //得到分类保存后的主键id,保存商品分类筛选属性关系
            PmsProductCategoryAttributeRelation pmsProductCategoryAttributeRelation = new PmsProductCategoryAttributeRelation();
            pmsProductCategoryAttributeRelation.setProductCategoryId(productCategory.getId());
            pmsProductCategoryAttributeRelation.setProductAttributeId(arrayId);
            list.add(pmsProductCategoryAttributeRelation);
        }

        return  relationService.saveBatch(list);
    }

    @Override
    public boolean update(PmsProductCategoryDTO productCategoryDTO) {
        //保存商品分类
        PmsProductCategory productCategory = new PmsProductCategory();
        //通过BeanUtils 将productCategoryDTO 数据拷贝到productCateGory
        //为什么要拷贝 应为一定要通过this.save 去保存PmsProductCategory 因为只有它才能映射数据
        BeanUtils.copyProperties(productCategoryDTO,productCategory);
        if (productCategory.getParentId() == 0) {
            //没有默认值就为0
            productCategory.setLevel(0);
        } else {
            //如果有多级分类 根据parentId查出商品分类获取level+1
            //由于只有2级分类，直接设置为1
            productCategory.setLevel(1);
        }
        //修改
        this.updateById(productCategory);
        //删除已保存的关联属性
        QueryWrapper<PmsProductCategoryAttributeRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PmsProductCategoryAttributeRelation::getProductCategoryId,productCategory.getId());
        relationService.remove(queryWrapper);
        saveAttrRelation(productCategoryDTO,productCategory);
        return true;

    }
    /**
     * description: `getWithChildren`
     * version: 1.0
     * date: 2022/5/19 22:21
     * @author: objcat
     * @param
     * @return java.util.List<com.tulingxueyuan.mall.dto.ProductCateChildrenDTO>
     *     获取商品分类的一级分类和二级分类的下拉级联数据
     */
    @Override
    public List<ProductCateChildrenDTO> getWithChildren() {

        return productCategoryMapper.getWithChildren();
    }
}
