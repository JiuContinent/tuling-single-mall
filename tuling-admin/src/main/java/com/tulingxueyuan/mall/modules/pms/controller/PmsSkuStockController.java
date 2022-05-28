package com.tulingxueyuan.mall.modules.pms.controller;


import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsSkuStock;
import com.tulingxueyuan.mall.modules.pms.service.PmsSkuStockService;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * sku的库存 前端控制器
 * </p>
 *
 * @author XuShu
 * @since 2022-05-11
 */
@RestController
@RequestMapping("/sku")
public class PmsSkuStockController {
    @Autowired
    private PmsSkuStockService skuStockService;

    /**
     *     url:'/sku/'+pid,
     *     method:'get',
     *     params:params
     */
    @RequestMapping("/{pid}")
    public CommonResult getSKUId(@PathVariable Long pid,String keyword){
        List<PmsSkuStock> list = skuStockService.list(pid,keyword);
        return CommonResult.success(list);
    }
    /**
     *      for (PmsSkuStock pmsSkuStock : pmsSkuStocks) {
     *             skuStockService.updateById(pmsSkuStock);
     *         }
     *         pmsSkuStocks.forEach(skuStockService::updateById);
     *
     *         pmsSkuStocks.forEach(item ->{
     *             skuStockService.updateById(item);
     *         });
     * @param pmsSkuStocks
     * @return
     */

    @PostMapping("/update")
    public CommonResult updateSku(@RequestBody List<PmsSkuStock> pmsSkuStocks){
        boolean skuStockUpdate =skuStockService.skuStockUpdate(pmsSkuStocks);
        return CommonResult.success(skuStockUpdate);
    }
    @PostMapping("/sss/update")
    public CommonResult updassteSku(@RequestBody List<PmsSkuStock> pmsSkuStocks){
        boolean skuStockUpdate =skuStockService.skuStockUpdate(pmsSkuStocks);
        return CommonResult.success(skuStockUpdate);
    }
}

