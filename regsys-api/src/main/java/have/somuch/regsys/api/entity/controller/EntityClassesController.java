package have.somuch.regsys.api.entity.controller;

import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.api.entity.entity.EntityClasses;
import have.somuch.regsys.api.entity.query.EntityClassesQuery;
import have.somuch.regsys.api.entity.service.IEntityClassesService;
import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.utils.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 班级信息表 前端控制器
 * </p>
 *
 * @author isZhous
 * @since 2024-01-27
 */
@RestController
@RequestMapping("/entityclasses")
public class EntityClassesController extends BaseController {

    @Autowired
    private IEntityClassesService entityClassesService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:entityclasses:index")
    @GetMapping("/index")
    public JsonResult index(EntityClassesQuery query) {
        return entityClassesService.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "班级信息表", logType = LogType.INSERT)
    @RequiresPermissions("sys:entityclasses:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody EntityClasses entity) {
        return entityClassesService.edit(entity);
    }

    /**
     * 获取详情
     *
     * @param entityclassesId 记录ID
     * @return
     */
    @GetMapping("/info/{entityclassesId}")
    public JsonResult info(@PathVariable("entityclassesId") Integer entityclassesId) {
        return entityClassesService.info(entityclassesId);
    }

    /**
     * 更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "班级信息表", logType = LogType.UPDATE)
    @RequiresPermissions("sys:entityclasses:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody EntityClasses entity) {
        return entityClassesService.edit(entity);
    }

    /**
     * 删除记录
     *
     * @param entityclassesIds 记录ID
     * @return
     */
    @Log(title = "班级信息表", logType = LogType.DELETE)
    @RequiresPermissions("sys:entityclasses:delete")
    @DeleteMapping("/delete/{entityclassesIds}")
    public JsonResult delete(@PathVariable("entityclassesIds") Integer[] entityclassesIds) {
        return entityClassesService.deleteByIds(entityclassesIds);
    }

}