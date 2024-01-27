package have.somuch.regsys.api.entity.controller;

import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.api.entity.entity.EntityCollege;
import have.somuch.regsys.api.entity.query.EntityCollegeQuery;
import have.somuch.regsys.api.entity.service.IEntityCollegeService;
import annotation.have.somuch.regsys.common.Log;
import utils.have.somuch.regsys.common.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 学院信息表 前端控制器
 * </p>
 *
 * @author isZhous
 * @since 2024-01-27
 */
@RestController
@RequestMapping("/entitycollege")
public class EntityCollegeController extends BaseController {

    @Autowired
    private IEntityCollegeService entityCollegeService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:entitycollege:index")
    @GetMapping("/index")
    public JsonResult index(EntityCollegeQuery query) {
        return entityCollegeService.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "学院信息表", logType = LogType.INSERT)
    @RequiresPermissions("sys:entitycollege:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody EntityCollege entity) {
        return entityCollegeService.edit(entity);
    }

    /**
     * 获取详情
     *
     * @param entitycollegeId 记录ID
     * @return
     */
    @GetMapping("/info/{entitycollegeId}")
    public JsonResult info(@PathVariable("entitycollegeId") Integer entitycollegeId) {
        return entityCollegeService.info(entitycollegeId);
    }

    /**
     * 更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "学院信息表", logType = LogType.UPDATE)
    @RequiresPermissions("sys:entitycollege:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody EntityCollege entity) {
        return entityCollegeService.edit(entity);
    }

    /**
     * 删除记录
     *
     * @param entitycollegeIds 记录ID
     * @return
     */
    @Log(title = "学院信息表", logType = LogType.DELETE)
    @RequiresPermissions("sys:entitycollege:delete")
    @DeleteMapping("/delete/{entitycollegeIds}")
    public JsonResult delete(@PathVariable("entitycollegeIds") Integer[] entitycollegeIds) {
        return entityCollegeService.deleteByIds(entitycollegeIds);
    }

}