package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsCommentReplayMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsCommentReplay;
import com.tulingxueyuan.mall.modules.pms.service.PmsCommentReplayService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 产品评价回复表 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2022-05-11
 */
@Service
@Transactional
public class PmsCommentReplayServiceImpl extends ServiceImpl<PmsCommentReplayMapper, PmsCommentReplay> implements PmsCommentReplayService {

}
