package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductAttributeMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeCategory;
import com.tulingxueyuan.mall.dto.RelationAttrInfoDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeCategoryService;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品属性参数表 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2022-05-11
 */
@Service
@Transactional
public class PmsProductAttributeServiceImpl extends ServiceImpl<PmsProductAttributeMapper, PmsProductAttribute> implements PmsProductAttributeService {

    @Resource
    private PmsProductAttributeCategoryService service;
    @Resource
    private PmsProductAttributeMapper productAttributeMapper;
    @Override
    public Page list(Long cid, Integer type, Integer pageNum, Integer pageSize) {
      Page page  = new Page(pageNum,pageNum);
      QueryWrapper<PmsProductAttribute> queryWrapper = new QueryWrapper<>();
      queryWrapper.lambda().eq(PmsProductAttribute::getProductAttributeCategoryId,cid)
              .eq(PmsProductAttribute::getType,type)
              .orderByAsc(PmsProductAttribute::getSort);
        return this.page(page,queryWrapper);
    }

    @Override
    public boolean create(PmsProductAttribute pmsProductAttribute) {
        //1. 保存商品属性
        boolean save = this.save(pmsProductAttribute);
        if (save) {
            //2.更新对应属性、参数数量
            //   1. 先查询商品类型，2.直接跟新update +1
            UpdateWrapper<PmsProductAttributeCategory> updateWrapper = new UpdateWrapper<>();
            //属性
            if (pmsProductAttribute.getType()==0) {
                updateWrapper.setSql("attribute_count=attribute_count+1");
                //用这种方式要先查 updateWrapper.lambda().set(PmsProductAttributeCategory.::getAttributeCount,+1)
            }
            //参数
            else if(pmsProductAttribute.getType()==1){
                updateWrapper.setSql("param_count=param_count+1");
            }
            //根据属性的类型id进行更新
            updateWrapper.lambda().eq(PmsProductAttributeCategory::getId,pmsProductAttribute.getProductAttributeCategoryId());
            service.update(updateWrapper);
        }
        return save;
    }

    /**
     * 删除 或者批量删除
     */
    @Override
    public boolean delete(List<Long> ids) {
        //判断集合中的list是否是空
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }
        //得到当前属性的列别
        PmsProductAttribute  productAttribute =null;
        for (Long id :ids){
            productAttribute=this.getById(id);
            if (productAttribute!=null) {
                break;
            }
        }
        //删除属性 得到删除后的数量
        int length = productAttributeMapper.deleteBatchIds(ids);
        if (length>0&&productAttribute!=null) {
            //在原有的基础上进行减操作
        UpdateWrapper<PmsProductAttributeCategory> updateWrapper = new UpdateWrapper<>();
        //属性
        if (productAttribute.getType()==0) {
            updateWrapper.setSql("attribute_count=attribute_count-"+length);
            //用这种方式要先查 updateWrapper.lambda().set(PmsProductAttributeCategory.::getAttributeCount,+1)
        }
        //参数
        else if(productAttribute.getType()==1){
            updateWrapper.setSql("param_count=param_count-"+length);
        }
        //根据属性的类型id进行更新
        updateWrapper.lambda().eq(PmsProductAttributeCategory::getId,productAttribute.getProductAttributeCategoryId());
        service.update(updateWrapper);
        }
        return length>0;
    }

    @Override
    public List<RelationAttrInfoDTO> getRelationAttrInfoByCid(Long cId) {
        return productAttributeMapper.getRelationAttrInfoByCid(cId);
    }
}
