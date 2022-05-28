package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mall.dto.ProductConditionDTO;
import com.tulingxueyuan.mall.dto.ProductSaveParamsDTO;
import com.tulingxueyuan.mall.dto.ProductUpdateInitDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 *
 * @author XuShu
 * @since 2022-05-11
 */
@RestController
@RequestMapping("/product")
public class PmsProductController {

    @Autowired
    PmsProductService productService;
/**
 *     url:'/product/list',
 *     method:'get',
 *     params:params
 *
 *      const defaultListQuery = {
 *     keyword: null,
 *     pageNum: 1,
 *     pageSize: 5,
 *     publishStatus: null,  axios 如果设置的是data属性就是以json的方式传递
 *     verifyStatus: null,   axios 如果设置的是params属性就是以url参数的方式传递
 *     productSn: null,      如果传递是URLSearchParms 会以formdata 的方式传递
 *     productCategoryId: null,
 *     brandId: null
 *   };
 */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public CommonResult getProductList(ProductConditionDTO conditionDTO){
        Page page = productService.getProductList(conditionDTO);
        return CommonResult.success(CommonPage.restPage(page));
    }
    /**  商品的逻辑删除
     *   url:'/product/update/deleteStatus',
     *     method:'post',
     *     params:params
     */
    @RequestMapping(value = "/update/deleteStatus")
    public CommonResult deleteProductStatus(@RequestParam("ids") List<Long> ids){
        boolean b = productService.removeByIds(ids);
        if (b){
            return CommonResult.success(b);
        }
        return CommonResult.failed();
    }
    /**
     *  更新是否新品的状态
     * export function updateNewStatus(params) {
     *   return request({
     *     url:'/product/update/newStatus',
     *     method:'post',
     *     params:params
     *   })
     * }
     */
    @RequestMapping(value = "/update/newStatus",method = RequestMethod.POST)
    public CommonResult updateNewStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("newStatus") Integer newStatus){
        boolean result =productService.getProductStatus(newStatus,ids,PmsProduct::getNewStatus);
        if (result) {
            return CommonResult.success(result);
        }
        return CommonResult.failed();
    }
    /**  跟新 是否推荐
     * export function updateRecommendStatus(params) {
     *   return request({
     *     url:'/product/update/recommendStatus',
     *     method:'post',
     *     params:params
     *   })
     * }
     */
    @RequestMapping(value = "/update/recommendStatus",method = RequestMethod.POST)
    public CommonResult updateRecommendStatus(@RequestParam("ids") List<Long>  ids,
                                             @RequestParam("recommendStatus") Integer recommendStatus){
        boolean productStatus = productService.getProductStatus(recommendStatus, ids, PmsProduct::getRecommandStatus);
        if (productStatus) {
            return CommonResult.success(productStatus);
        }
        return CommonResult.failed();
    }
    /**
     * 更新是否上架
     * export function updatePublishStatus(params) {
     *   return request({
     *     url:'/product/update/publishStatus',
     *     method:'post',
     *     params:params
     *   })
     * }
     */
    @RequestMapping(value = "/update/publishStatus",method = RequestMethod.POST)
    public CommonResult updatePublishStatus(@RequestParam("ids") List<Long> ids,
                                           @RequestParam("publishStatus") Integer publishStatus){
        boolean productStatus = productService.getProductStatus(publishStatus, ids, PmsProduct::getPublishStatus);
        if (productStatus) {
            return CommonResult.success(productStatus);
        }
        return CommonResult.failed();
    }
    /**   商品的添加
     *    url:'/product/create',
     *     method:'post',
     *     data:data  json
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public CommonResult createProduct(@RequestBody @Valid  ProductSaveParamsDTO productSaveParamsDTO){
        boolean result =productService.createProduct(productSaveParamsDTO);
        if (result) {
            return CommonResult.success(result);
        }
        return CommonResult.failed();
    }
    /**  获取编辑状态下商品信息
     * url:'/product/updateInfo/'+id,
     *     method:'get',
     */
    @RequestMapping(value = "/updateInfo/{id}",method = RequestMethod.GET)
    public CommonResult getProductInfo(@PathVariable Long id){
        ProductUpdateInitDTO updateInitDTO =productService.getProductInfo(id);
        return CommonResult.success(updateInitDTO);
    }
    /**  商品列表的修改保存
     *   url:'/product/update/'+id,
     *     method:'post',
     *     data:data
     */
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public CommonResult updateProductInfo(@RequestBody @Valid ProductSaveParamsDTO productSaveParamsDTO){
        boolean  result =productService.updateProductInfo(productSaveParamsDTO);
        if (result) {
            return CommonResult.success(result);
        }
        return CommonResult.failed();
    }
}

