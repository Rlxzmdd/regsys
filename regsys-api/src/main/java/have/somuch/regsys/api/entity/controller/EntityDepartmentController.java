package have.somuch.regsys.api.entity.controller;

import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.api.entity.entity.EntityDepartment;
import have.somuch.regsys.api.entity.query.EntityDepartmentQuery;
import have.somuch.regsys.api.entity.service.IEntityDepartmentService;
import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.utils.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 部门信息表 前端控制器
 * </p>
 *
 * @author isZhous
 * @since 2024-01-27
 */
@RestController
@RequestMapping("/entitydepartment")
public class EntityDepartmentController extends BaseController {

    @Autowired
    private IEntityDepartmentService entityDepartmentService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:entitydepartment:index")
    @GetMapping("/index")
    public JsonResult index(EntityDepartmentQuery query) {
        return entityDepartmentService.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "部门信息表", logType = LogType.INSERT)
    @RequiresPermissions("sys:entitydepartment:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody EntityDepartment entity) {
        return entityDepartmentService.edit(entity);
    }

    /**
     * 获取详情
     *
     * @param entitydepartmentId 记录ID
     * @return
     */
    @GetMapping("/info/{entitydepartmentId}")
    public JsonResult info(@PathVariable("entitydepartmentId") Integer entitydepartmentId) {
        return entityDepartmentService.info(entitydepartmentId);
    }

    /**
     * 更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "部门信息表", logType = LogType.UPDATE)
    @RequiresPermissions("sys:entitydepartment:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody EntityDepartment entity) {
        return entityDepartmentService.edit(entity);
    }

    /**
     * 删除记录
     *
     * @param entitydepartmentIds 记录ID
     * @return
     */
    @Log(title = "部门信息表", logType = LogType.DELETE)
    @RequiresPermissions("sys:entitydepartment:delete")
    @DeleteMapping("/delete/{entitydepartmentIds}")
    public JsonResult delete(@PathVariable("entitydepartmentIds") Integer[] entitydepartmentIds) {
        return entityDepartmentService.deleteByIds(entitydepartmentIds);
    }

}