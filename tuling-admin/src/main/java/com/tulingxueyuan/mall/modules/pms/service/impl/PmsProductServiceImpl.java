package com.tulingxueyuan.mall.modules.pms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mall.dto.ProductConditionDTO;
import com.tulingxueyuan.mall.dto.ProductSaveParamsDTO;
import com.tulingxueyuan.mall.dto.ProductUpdateInitDTO;
import com.tulingxueyuan.mall.modules.pms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.List;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2022-05-11
 */
@Service
@Transactional
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements PmsProductService {

    @Autowired
    private PmsProductMapper productMapper;
    @Autowired
    private PmsMemberPriceService memberPriceService;
    @Autowired
    private PmsProductLadderService productLadderService;
    @Autowired
    private PmsProductFullReductionService pmsProductFullReductionService;
    @Autowired
    private PmsSkuStockService skuStockService;
    @Autowired
    private PmsProductAttributeValueService productAttributeValueService;
    @Override
    public Page getProductList(ProductConditionDTO conditionDTO) {
        Page page = new Page(conditionDTO.getPageNum(),conditionDTO.getPageSize());
        LambdaQueryWrapper<PmsProduct> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //商品名称  使用字符串工具集 hutool
        if (!StrUtil.isBlank(conditionDTO.getKeyword())) {
            lambdaQueryWrapper.like(PmsProduct::getName,conditionDTO.getKeyword());
        }
        //商品货号
        if (!StrUtil.isBlank(conditionDTO.getProductSn())) {
            lambdaQueryWrapper.eq(PmsProduct::getProductSn,conditionDTO.getProductSn());
        }
        //商品分类
        if (conditionDTO.getProductCategoryId()!=null && conditionDTO.getProductCategoryId()>0) {
            lambdaQueryWrapper.eq(PmsProduct::getProductCategoryId,conditionDTO.getProductCategoryId());
        }
        //商品品牌
        if (conditionDTO.getBrandId()!=null && conditionDTO.getBrandId()>0) {
            lambdaQueryWrapper.eq(PmsProduct::getBrandId,conditionDTO.getBrandId());
        }
        //上架状态
        if (conditionDTO.getPublishStatus()!=null && conditionDTO.getPublishStatus()>0) {
            lambdaQueryWrapper.eq(PmsProduct::getPublishStatus,conditionDTO.getPublishStatus());
        }
        //审核状态
        if (conditionDTO.getVerifyStatus()!=null) {
            lambdaQueryWrapper.eq(PmsProduct::getVerifyStatus,conditionDTO.getVerifyStatus());
        }
        lambdaQueryWrapper.orderByAsc(PmsProduct::getSort);
        return this.page(page,lambdaQueryWrapper);
    }

    @Override
    public boolean getProductStatus(Integer status, List<Long> ids, SFunction<PmsProduct,?> getPublishStatus) {
        UpdateWrapper<PmsProduct> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(getPublishStatus,status)
                .in(PmsProduct::getId,ids);  //where in ids
        return this.update(updateWrapper);
    }

    /**
     * 添加
     * @param productSaveParamsDTO
     * @return
     */
    @Override
    public boolean createProduct(ProductSaveParamsDTO productSaveParamsDTO) {
        //1.保存商品基本信息  --商品主表
        PmsProduct product = productSaveParamsDTO;
        product.setId(null);
        boolean result = this.save(product);
        if (result) {
            //为了解决  前端会传入其他促销方式的空数据进来
            switch (product.getPromotionType()){
                case 2:
                    //2. 会员价格、减满价格
                    SaveManyList(productSaveParamsDTO.getMemberPriceList(),product.getId(),memberPriceService);
                    break;
                case 3:
                    //3.阶梯价格
                    SaveManyList(productSaveParamsDTO.getProductLadderList(), product.getId(),productLadderService);
                    break;
                case 4:
                    //4. 减满价格
                    SaveManyList(productSaveParamsDTO.getProductFullReductionList(),product.getId(),pmsProductFullReductionService);
                    break;
            }



            //5. skw
            SaveManyList(productSaveParamsDTO.getSkuStockList(),product.getId(),skuStockService);
            //6. spu
            SaveManyList(productSaveParamsDTO.getProductAttributeValueList(),product.getId(),productAttributeValueService);
        }
        return result;
    }

    /**
     * 编辑数据初始化
     * @param id
     * @return
     */
    @Override
    public ProductUpdateInitDTO getProductInfo(Long id) {
        return productMapper.getProductInfo(id);
    }
    /**  商品列表 商品修改保存
     * description: updateProductInfo
     * version: 1.0
     * date: 2022/5/22 0:26
     * @author: objcat
     * @param productSaveParamsDTO
     * @return boolean
     */

    @Override
    public boolean updateProductInfo(ProductSaveParamsDTO productSaveParamsDTO) {
        //1.保存商品基本信息  --商品主表
        PmsProduct product = productSaveParamsDTO;
        boolean result = this.updateById(product);
        if (result) {
            //为了解决  前端会传入其他促销方式的空数据进来
            switch (product.getPromotionType()){
                case 2:
                    //根据商品id删除
                    DeleteManyListByProductId(product.getId(),memberPriceService);
                    //2. 会员价格、减满价格
                    SaveManyList(productSaveParamsDTO.getMemberPriceList(),product.getId(),memberPriceService);
                    break;
                case 3:
                    //根据商品id删除
                    DeleteManyListByProductId(product.getId(),productLadderService);
                    //3.阶梯价格
                    SaveManyList(productSaveParamsDTO.getProductLadderList(), product.getId(),productLadderService);
                    break;
                case 4:
                    //根据商品id删除
                    DeleteManyListByProductId(product.getId(),pmsProductFullReductionService);
                    //4. 减满价格
                    SaveManyList(productSaveParamsDTO.getProductFullReductionList(),product.getId(),pmsProductFullReductionService);
                    break;
            }

            //根据商品id删除
            DeleteManyListByProductId(product.getId(),skuStockService);
            //5. skw
            SaveManyList(productSaveParamsDTO.getSkuStockList(),product.getId(),skuStockService);
            //根据商品id删除
            DeleteManyListByProductId(product.getId(),productAttributeValueService);
            //6. spu
            SaveManyList(productSaveParamsDTO.getProductAttributeValueList(),product.getId(),productAttributeValueService);
        }
        return result;
    }

    /**
     *  公共方法 ： 保存会员价格、阶梯价格、减满价格、sku、spu 商品的关联属性
     *   统一 ： 需要设置商品id, 都需要批量保存
     * @param list
     * @param productId
     * @param service
     */
    public void SaveManyList(List<?> list, Long productId, IService service){
        //如果 数据为空， 或者长度为0  不做任何操作
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        try {
            //循环 反射 赋值商品id
        for (Object obj :list){
                Method setProductIdMethod = obj.getClass().getMethod("setProductId",Long.class);
                //在修改状态清除主键id
                Method setId =obj.getClass().getMethod("setId",Long.class);
                setId.invoke(obj,(Long)null);
                //调用setProductId
            setProductIdMethod.invoke(obj,productId);
        }
        service.saveBatch(list);
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }
    /**
     * 根据商品id 删除关联数据
     */
    public void DeleteManyListByProductId(Long productId,IService service){
        QueryWrapper queryWrapper  = new QueryWrapper();
        queryWrapper.eq("product_id",productId);
        service.remove(queryWrapper);
    }
}