package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsSkuStockMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsSkuStock;
import com.tulingxueyuan.mall.modules.pms.service.PmsSkuStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Queue;

/**
 * <p>
 * sku的库存 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2022-05-11
 */
@Service
@Transactional
public class PmsSkuStockServiceImpl extends ServiceImpl<PmsSkuStockMapper, PmsSkuStock> implements PmsSkuStockService {

    @Override
    public List<PmsSkuStock> list(Long id,String keyword) {
        QueryWrapper<PmsSkuStock> queryWrapper =  new QueryWrapper();
        queryWrapper.lambda().eq(PmsSkuStock::getProductId,id);
        if(StringUtils.hasText(keyword)){
            queryWrapper.lambda().likeRight(PmsSkuStock::getSkuCode,keyword);
        }
        List<PmsSkuStock> list = this.list(queryWrapper);
        return list;

    }

    @Override
    public Boolean skuStockUpdate(List<PmsSkuStock> pmsSkuStocks) {
        try {
            pmsSkuStocks.forEach(baseMapper::updateById);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
