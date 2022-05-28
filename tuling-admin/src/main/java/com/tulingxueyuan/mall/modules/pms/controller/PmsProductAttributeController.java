package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import com.tulingxueyuan.mall.dto.RelationAttrInfoDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 前端控制器
 * </p>
 *
 * @author XuShu
 * @since 2022-05-11
 */
@RestController
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {
    @Autowired
    private PmsProductAttributeService service;
    /**  商品分类 --- 商品属性数据列表
     *   url:'/productAttribute/list/'+cid,
     *     method:'get',
     *     params:params
     */
    @RequestMapping(value = "/list/{cid}",method = RequestMethod.GET)
    public CommonResult<CommonPage> getListProductAttribute(@PathVariable Long cid,
                                                           @RequestParam("type") Integer type,
                                                           @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                           @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize
                                                           ){
        Page list = service.list(cid, type, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(list));
    }
    /**   商品属性数据列表  添加
     *    url:'/productAttribute/create',
     *     method:'post',
     *     data:data
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public CommonResult getProductAttributeCreate(@RequestBody PmsProductAttribute pmsProductAttribute){
        boolean save = service.create(pmsProductAttribute);
        if (save) {
            return CommonResult.success(save);
        }
        return CommonResult.failed();
    }
    /**
     *   url:'/productAttribute/update/'+id,
     *     method:'post',
     *     data:data
     */
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public CommonResult getProductAttributeUpdate(PmsProductAttribute pmsProductAttribute){
        boolean b = service.updateById(pmsProductAttribute);
        if (b) {
            return CommonResult.success(b);
        }
        return CommonResult.failed();
    }
    /**  批量删除和单个删除
     *  url:'/productAttribute/delete',
     *     method:'post',
     *     data:data
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public CommonResult getProductAttributeDelete(@RequestParam("ids") List<Long> ids){
        boolean b = service.delete(ids);
        if (b){
            return CommonResult.success(b);
        }
        return CommonResult.failed();
    }
    /**  根据商品的分类id获取关联的筛选属性
     *  url:'/productAttribute/attrInfo/'+productCategoryId,
     *     method:'get'
     */
    @RequestMapping(value = "/attrInfo/{cId}",method = RequestMethod.GET)
    public CommonResult getReleaseAttributeByCid(@PathVariable Long cId){
        List<RelationAttrInfoDTO> list=service.getRelationAttrInfoByCid(cId);
        return CommonResult.success(list);
    }
}

