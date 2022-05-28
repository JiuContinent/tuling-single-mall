package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsAlbumMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsAlbum;
import com.tulingxueyuan.mall.modules.pms.service.PmsAlbumService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 相册表 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2022-05-11
 */
@Service
@Transactional
public class PmsAlbumServiceImpl extends ServiceImpl<PmsAlbumMapper, PmsAlbum> implements PmsAlbumService {

}
