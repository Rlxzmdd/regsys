package have.somuch.regsys.system.service;

import have.somuch.regsys.common.common.IBaseService;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.dto.RolePermissionDto;
import have.somuch.regsys.system.entity.Role;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-31
 */
public interface IRoleService extends IBaseService<Role> {

    /**
     * 获取角色列表
     *
     * @return
     */
    JsonResult getRoleList();

    /**
     * 获取角色菜单列表
     *
     * @param roleId 角色ID
     * @return
     */
    JsonResult getPermissionList(Integer roleId);

    /**
     * 分配角色菜单数据
     *
     * @param rolePermissionDto 角色权限集合
     * @return
     */
    JsonResult savePermission(RolePermissionDto rolePermissionDto);

}
