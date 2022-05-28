package com.tulingxueyuan.mall.dto;

import com.tulingxueyuan.mall.modules.ums.model.UmsResource;
import com.tulingxueyuan.mall.modules.ums.model.UmsRole;
import lombok.Data;

import java.util.List;

/**
 * @Author JiuContinent
 * @create 2022/5/27 20:49
 */
@Data
public class ResourceRoleDTO extends UmsResource {
    private List<UmsRole> roleList;
}
