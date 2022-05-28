package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductOperateLogMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductOperateLog;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductOperateLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2022-05-11
 */
@Service
@Transactional
public class PmsProductOperateLogServiceImpl extends ServiceImpl<PmsProductOperateLogMapper, PmsProductOperateLog> implements PmsProductOperateLogService {

}
