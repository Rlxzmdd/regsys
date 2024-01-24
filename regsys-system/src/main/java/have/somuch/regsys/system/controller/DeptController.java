package have.somuch.regsys.system.controller;


import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.entity.Dept;
import have.somuch.regsys.system.query.DeptQuery;
import have.somuch.regsys.system.service.IDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-03
 */
@RestController
@RequestMapping("/dept")
public class DeptController extends BaseController {

    @Autowired
    private IDeptService deptService;

    /**
     * 获取部门列表
     *
     * @param deptQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:dept:index")
    @GetMapping("/index")
    public JsonResult index(DeptQuery deptQuery) {
        return deptService.getList(deptQuery);
    }

    /**
     * 添加部门
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "部门管理", logType = LogType.INSERT)
    @RequiresPermissions("sys:dept:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody Dept entity) {
        return deptService.edit(entity);
    }

    /**
     * 编辑部门
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "部门管理", logType = LogType.UPDATE)
    @RequiresPermissions("sys:dept:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody Dept entity) {
        return deptService.edit(entity);
    }

    /**
     * 删除部门
     *
     * @param deptId 部门ID
     * @return
     */
    @Log(title = "部门管理", logType = LogType.DELETE)
    @RequiresPermissions("sys:dept:delete")
    @DeleteMapping("/delete/{deptId}")
    public JsonResult delete(@PathVariable("deptId") Integer deptId) {
        return deptService.deleteById(deptId);
    }

    /**
     * 获取部门列表
     *
     * @return
     */
    @GetMapping("/getDeptList")
    public JsonResult getDeptList() {
        return deptService.getDeptList();
    }

}
