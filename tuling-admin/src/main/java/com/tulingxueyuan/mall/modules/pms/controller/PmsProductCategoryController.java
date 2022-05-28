package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mall.dto.PmsProductCategoryDTO;
import com.tulingxueyuan.mall.dto.ProductCateChildrenDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 产品分类 前端控制器
 * </p>
 *
 * @author XuShu
 * @since 2022-05-11
 */
@RestController
@RequestMapping("/productCategory")
public class PmsProductCategoryController {
    @Autowired
    PmsProductCategoryService productCategoryService;

    @RequestMapping(value = "/list/{parentId}", method = RequestMethod.GET)
    public CommonResult<CommonPage<PmsProductCategory>> getList(@PathVariable Long parentId,
                                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        Page page = productCategoryService.list(parentId, pageNum, pageSize);
        /**
         * CommonPage.restPage(page) : 拆封分页的数据
         * CommonResult.success() : 公共Result 的返回
         */
        return CommonResult.success(CommonPage.restPage(page));
    }

    /**
     * 修改状态栏的接口
     * url:'/productCategory/update/navStatus',
     * method:'post',
     * data:data
     * data的参数
     * data.append('ids',ids);  当前的的id
     * data.append('navStatus',row.navStatus);  是否显示在导航栏的状态
     */
    @RequestMapping(value = "/update/navStatus", method = RequestMethod.POST)
    public CommonResult updateNavStatus(@RequestParam(value = "ids") List<Long> ids,
                                        @RequestParam(value = "navStatus") Integer navStatus) {
        boolean b = productCategoryService.updateNavStatus(ids, navStatus);
        if (b) {
            //跟新成功
            return CommonResult.success(b);
        } else {
            //跟新失败
            return CommonResult.failed();
        }
    }

    /**
     * 状态栏是否显示的接口
     * data.append('ids',ids);
     * data.append('showStatus',row.showStatus);
     * url:'/productCategory/update/showStatus',
     * method:'post',
     * data:data
     */
    @RequestMapping(value = "/update/showStatus", method = RequestMethod.POST)
    public CommonResult showStatusByUpdate(@RequestParam("ids") List<Long> ids, @RequestParam("showStatus") Integer showStatus) {
        boolean flag = productCategoryService.updateShowStatus(ids, showStatus);
        if (flag) {
            //跟新成功
            return CommonResult.success(flag);
        } else {
            //跟新失败
            return CommonResult.failed();
        }
    }

    /**
     * url:'/productCategory/delete/'+id,
     * method:'post'
     */
    @RequestMapping("/delete/{id}")
    public CommonResult deleteById(@PathVariable("id") Integer id) {
        boolean b = productCategoryService.removeById(id);
        if (!b) {
            //跟新失败
            return CommonResult.failed();
        }
        return CommonResult.success(b);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult create(@RequestBody PmsProductCategoryDTO productCategoryDTO) {
        boolean save = productCategoryService.CustomSave(productCategoryDTO);
        if (save) {
            return CommonResult.success(save);
        }
        return CommonResult.success(save);
    }

    /**
     * 根据id获取商品分类
     * url:'/productCategory/'+id,
     * method:'get'
     */
    @RequestMapping("/{id}")
    public CommonResult getById(@PathVariable("id") Long id) {
        PmsProductCategory byId = productCategoryService.getById(id);
        return CommonResult.success(byId);
    }

    /**
     * 修改商品
     * url:'/productCategory/update/'+id,
     * method:'post',
     * data:data
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult updateCategory(@RequestBody PmsProductCategoryDTO productCategoryDTO) {
        boolean b = productCategoryService.update(productCategoryDTO);
        if (b) {
            return CommonResult.success(b);
        }
        return CommonResult.failed();
    }
    /**  获取商品一级分类和二级分类的下拉级联数据
     *  url:'/productCategory/list/withChildren',
     *     method:'get'
     */
    @RequestMapping("/list/withChildren")
    public CommonResult getWithChildren(){
        List<ProductCateChildrenDTO> list = productCategoryService.getWithChildren();
        return CommonResult.success(list);
    }
}

