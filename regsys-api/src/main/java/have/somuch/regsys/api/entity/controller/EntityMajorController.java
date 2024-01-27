package have.somuch.regsys.api.entity.controller;

import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.api.entity.entity.EntityMajor;
import have.somuch.regsys.api.entity.query.EntityMajorQuery;
import have.somuch.regsys.api.entity.service.IEntityMajorService;
import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.utils.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 专业信息表 前端控制器
 * </p>
 *
 * @author isZhous
 * @since 2024-01-27
 */
@RestController
@RequestMapping("/entitymajor")
public class EntityMajorController extends BaseController {

    @Autowired
    private IEntityMajorService entityMajorService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:entitymajor:index")
    @GetMapping("/index")
    public JsonResult index(EntityMajorQuery query) {
        return entityMajorService.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "专业信息表", logType = LogType.INSERT)
    @RequiresPermissions("sys:entitymajor:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody EntityMajor entity) {
        return entityMajorService.edit(entity);
    }

    /**
     * 获取详情
     *
     * @param entitymajorId 记录ID
     * @return
     */
    @GetMapping("/info/{entitymajorId}")
    public JsonResult info(@PathVariable("entitymajorId") Integer entitymajorId) {
        return entityMajorService.info(entitymajorId);
    }

    /**
     * 更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "专业信息表", logType = LogType.UPDATE)
    @RequiresPermissions("sys:entitymajor:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody EntityMajor entity) {
        return entityMajorService.edit(entity);
    }

    /**
     * 删除记录
     *
     * @param entitymajorIds 记录ID
     * @return
     */
    @Log(title = "专业信息表", logType = LogType.DELETE)
    @RequiresPermissions("sys:entitymajor:delete")
    @DeleteMapping("/delete/{entitymajorIds}")
    public JsonResult delete(@PathVariable("entitymajorIds") Integer[] entitymajorIds) {
        return entityMajorService.deleteByIds(entitymajorIds);
    }

}