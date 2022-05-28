package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.dto.ProductAttributeCateDTO;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeCategory;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 前端控制器
 * </p>
 *
 * @author XuShu
 * @since 2022-05-11
 */
@RestController
@RequestMapping("/productAttribute/category")
public class PmsProductAttributeCategoryController {
    /**
     * 查询商品分类的列表
     * url:'/productAttribute/category/list',
     * method:'get',
     * params:params
     */
    @Autowired
    private PmsProductAttributeCategoryService productAttributeCategoryService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<PmsProductAttributeCategory>> getList(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize) {
        Page page = productAttributeCategoryService.list(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(page));
    }

    /**
     * 添加商品类型
     * url:'/productAttribute/category/create',
     * method:'post',
     * data:data
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult addProductAttributeCategory(PmsProductAttributeCategory productAttributeCategory) {
        boolean flag = productAttributeCategoryService.add(productAttributeCategory);
        if (flag) {
            return CommonResult.success(flag);
        }
        return CommonResult.failed();
    }

    /**
     * url:'/productAttribute/category/update/'+id,
     * method:'post',
     * data:data
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult updateProductAttributeCategory(PmsProductAttributeCategory productAttributeCategory) {
        boolean update = productAttributeCategoryService.updateById(productAttributeCategory);
        if (update) {
            return CommonResult.success(update);
        }
        return CommonResult.failed();
    }

    /**
     * url:'/productAttribute/category/delete/'+id,
     * method:输入的风格的地方
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public CommonResult deleteProductAttributeCateGory(@PathVariable("id") Integer id) {
        boolean b = productAttributeCategoryService.removeById(id);
        if (b) {
            return CommonResult.success(b);
        }
        return CommonResult.failed();
    }
    /**  筛选属性的下拉级联数据
     *  url:'/productAttribute/category/list/withAttr',
     *     method:'get'
     */
    @RequestMapping(value = "/list/withAttr",method = RequestMethod.GET)
    public CommonResult getListWithAttr(){
        List<ProductAttributeCateDTO> list =productAttributeCategoryService.getListWithAttr();
        return CommonResult.success(list);
    }

}

