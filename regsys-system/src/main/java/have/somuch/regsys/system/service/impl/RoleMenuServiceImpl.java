package have.somuch.regsys.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.system.entity.RoleMenu;
import have.somuch.regsys.system.mapper.RoleMenuMapper;
import have.somuch.regsys.system.service.IRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色菜单关联表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-30
 */
@Service
public class RoleMenuServiceImpl extends BaseServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    /**
     * 根据角色ID删除角色菜单关系数据
     *
     * @param roleId 角色ID
     * @return
     */
    @Override
    public boolean deleteRoleMenus(Integer roleId) {
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        boolean result = remove(queryWrapper);
        return result;
    }
}
