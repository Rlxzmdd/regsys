package have.somuch.regsys.system.controller;


import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.dto.RolePermissionDto;
import have.somuch.regsys.system.entity.Role;
import have.somuch.regsys.system.query.RoleQuery;
import have.somuch.regsys.system.service.IRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-31
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

    /**
     * 获取角色列表
     *
     * @param roleQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:role:index")
    @GetMapping("/index")
    public JsonResult index(RoleQuery roleQuery) {
        return roleService.getList(roleQuery);
    }

    /**
     * 添加角色
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "角色管理", logType = LogType.INSERT)
    @RequiresPermissions("sys:role:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody Role entity) {
        return roleService.edit(entity);
    }

    /**
     * 编辑角色
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "角色管理", logType = LogType.UPDATE)
    @RequiresPermissions("sys:role:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody Role entity) {
        return roleService.edit(entity);
    }

    /**
     * 删除角色
     *
     * @param roleIds 角色ID
     * @return
     */
    @Log(title = "角色管理", logType = LogType.DELETE)
    @RequiresPermissions("sys:role:delete")
    @DeleteMapping("/delete/{roleIds}")
    public JsonResult delete(@PathVariable("roleIds") Integer[] roleIds) {
        return roleService.deleteByIds(roleIds);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "角色管理", logType = LogType.STATUS)
    @RequiresPermissions("sys:role:status")
    @PutMapping("/status")
    public JsonResult status(@RequestBody Role entity) {
        return roleService.setStatus(entity);
    }

    /**
     * 获取角色列表
     *
     * @return
     */
    @GetMapping("/getRoleList")
    public JsonResult getRoleList() {
        return roleService.getRoleList();
    }

    /**
     * 获取角色菜单列表
     *
     * @param roleId 角色ID
     * @return
     */
    @GetMapping("/getPermissionList/{roleId}")
    public JsonResult getPermissionList(@PathVariable("roleId") Integer roleId) {
        return roleService.getPermissionList(roleId);
    }

    /**
     * 分配角色权限数据
     *
     * @param rolePermissionDto 角色权限集合
     * @return
     */
    @RequiresPermissions("sys:role:permission")
    @PostMapping("/savePermission")
    public JsonResult savePermission(@RequestBody RolePermissionDto rolePermissionDto) {
        return roleService.savePermission(rolePermissionDto);
    }

}
