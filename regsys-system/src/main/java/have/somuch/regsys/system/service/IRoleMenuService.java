package have.somuch.regsys.system.service;

import have.somuch.regsys.common.common.IBaseService;
import have.somuch.regsys.system.entity.RoleMenu;

/**
 * <p>
 * 角色菜单关联表 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-30
 */
public interface IRoleMenuService extends IBaseService<RoleMenu> {

    /**
     * 根据角色ID删除角色菜单关系数据
     *
     * @param roleId
     */
    boolean deleteRoleMenus(Integer roleId);

}
