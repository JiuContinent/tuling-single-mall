package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductVertifyRecordMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductVertifyRecord;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductVertifyRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 商品审核记录 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2022-05-11
 */
@Service
@Transactional
public class PmsProductVertifyRecordServiceImpl extends ServiceImpl<PmsProductVertifyRecordMapper, PmsProductVertifyRecord> implements PmsProductVertifyRecordService {

}
