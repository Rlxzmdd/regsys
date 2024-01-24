package have.somuch.regsys.system.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.common.config.CommonConfig;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.dto.RolePermissionDto;
import have.somuch.regsys.system.entity.Menu;
import have.somuch.regsys.system.entity.Role;
import have.somuch.regsys.system.entity.RoleMenu;
import have.somuch.regsys.system.mapper.RoleMapper;
import have.somuch.regsys.system.mapper.RoleMenuMapper;
import have.somuch.regsys.system.query.RoleQuery;
import have.somuch.regsys.system.service.IMenuService;
import have.somuch.regsys.system.service.IRoleMenuService;
import have.somuch.regsys.system.service.IRoleService;
import have.somuch.regsys.system.utils.ShiroUtils;
import have.somuch.regsys.system.vo.role.RoleListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-31
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private IRoleMenuService roleMenuService;

    /**
     * 获取角色列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        RoleQuery roleQuery = (RoleQuery) query;
        // 查询条件
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        // 角色名称
        if (!StringUtils.isEmpty(roleQuery.getName())) {
            queryWrapper.like("name", roleQuery.getName());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询分页数据
        IPage<Role> page = new Page<>(roleQuery.getPage(), roleQuery.getLimit());
        IPage<Role> pageData = roleMapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            RoleListVo roleListVo = Convert.convert(RoleListVo.class, x);
            // TODO...
            return roleListVo;
        });
        return JsonResult.success(pageData);
    }

    /**
     * 添加或编辑
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Role entity) {
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
        }
        return super.edit(entity);
    }

    /**
     * 获取角色列表
     *
     * @return
     */
    @Override
    public JsonResult getRoleList() {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");
        return JsonResult.success(list(queryWrapper));
    }

    /**
     * 获取菜单列表
     *
     * @param roleId 角色ID
     * @return
     */
    @Override
    public JsonResult getPermissionList(Integer roleId) {
        List<Menu> menuList = menuService.getMenuAll();
        if (!menuList.isEmpty()) {
            // 获取角色菜单列表
            QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id", roleId);
            queryWrapper.select("menu_id");
            List<RoleMenu> roleMenuList = roleMenuMapper.selectList(queryWrapper);
            List<Integer> menuIds = roleMenuList.stream().map(p -> p.getMenuId()).collect(Collectors.toList());
            menuList.forEach(item -> {
                if (menuIds.contains(item.getId())) {
                    item.setChecked(true);
                    item.setOpen(true);
                }
            });
        }
        return JsonResult.success(menuList);
    }

    /**
     * 分配角色菜单权限数据
     *
     * @param rolePermissionDto 角色权限集合
     * @return
     */
    @Override
    public JsonResult savePermission(RolePermissionDto rolePermissionDto) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        // 角色ID校验
        if (StringUtils.isNull(rolePermissionDto.getRoleId())) {
            return JsonResult.error("角色ID不能位空");
        }
        // 同步删除角色菜单关系数据
        roleMenuService.deleteRoleMenus(rolePermissionDto.getRoleId());
        // 插入新的角色菜单关系数据
        List<RoleMenu> roleMenuList = new ArrayList<>();
        if (!StringUtils.isEmpty(rolePermissionDto.getMenuIds())) {
            for (String menuId : rolePermissionDto.getMenuIds()) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(rolePermissionDto.getRoleId());
                roleMenu.setMenuId(Integer.valueOf(menuId));
                roleMenuList.add(roleMenu);
            }
        }
        // 批量插入角色菜单关系数据
        roleMenuService.saveBatch(roleMenuList);
        return JsonResult.success("权限保存成功");
    }
}
