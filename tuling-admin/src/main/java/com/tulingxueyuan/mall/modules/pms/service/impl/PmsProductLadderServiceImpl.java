package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductLadderMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductLadder;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductLadderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 产品阶梯价格表(只针对同商品) 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2022-05-11
 */
@Service
@Transactional
public class PmsProductLadderServiceImpl extends ServiceImpl<PmsProductLadderMapper, PmsProductLadder> implements PmsProductLadderService {

}
