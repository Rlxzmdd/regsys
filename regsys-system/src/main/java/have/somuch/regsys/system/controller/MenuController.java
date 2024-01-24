package have.somuch.regsys.system.controller;


import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.entity.Menu;
import have.somuch.regsys.system.query.MenuQuery;
import have.somuch.regsys.system.service.IMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统菜单表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-30
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {

    @Autowired
    private IMenuService menuService;

    /**
     * 获取菜单列表
     *
     * @param menuQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:menu:index")
    @GetMapping("/index")
    public JsonResult index(MenuQuery menuQuery) {
        return menuService.getList(menuQuery);
    }

    /**
     * 获取菜单详情
     *
     * @param menuId 菜单ID
     * @return
     */
    @GetMapping("/info/{menuId}")
    public JsonResult info(@PathVariable("menuId") Integer menuId) {
        return menuService.info(menuId);
    }

    /**
     * 添加菜单
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "菜单管理", logType = LogType.INSERT)
    @RequiresPermissions("sys:menu:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody Menu entity) {
        return menuService.edit(entity);
    }

    /**
     * 编辑菜单
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "菜单管理", logType = LogType.UPDATE)
    @RequiresPermissions("sys:menu:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody Menu entity) {
        return menuService.edit(entity);
    }

    /**
     * 删除菜单
     *
     * @param menuId 菜单ID
     * @return
     */
    @Log(title = "菜单管理", logType = LogType.DELETE)
    @RequiresPermissions("sys:menu:delete")
    @DeleteMapping("/delete/{menuId}")
    public JsonResult delete(@PathVariable("menuId") Integer menuId) {
        return menuService.deleteById(menuId);
    }

    /**
     * 获取所有菜单列表
     *
     * @return
     */
    @GetMapping("/getMenuAll")
    public JsonResult getMenuAll() {
        List<Menu> menuList = menuService.getMenuAll();
        return JsonResult.success(menuList);
    }

}
