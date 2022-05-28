package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsAlbumPicMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsAlbumPic;
import com.tulingxueyuan.mall.modules.pms.service.PmsAlbumPicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 画册图片表 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2022-05-11
 */
@Service
@Transactional
public class PmsAlbumPicServiceImpl extends ServiceImpl<PmsAlbumPicMapper, PmsAlbumPic> implements PmsAlbumPicService {

}
