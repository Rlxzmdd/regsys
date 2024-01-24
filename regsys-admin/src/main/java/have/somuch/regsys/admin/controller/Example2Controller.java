package have.somuch.regsys.admin.controller;

import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.admin.entity.Example2;
import have.somuch.regsys.admin.query.Example2Query;
import have.somuch.regsys.admin.service.IExample2Service;
import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.utils.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 演示案例二 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2023-03-11
 */
@RestController
@RequestMapping("/example2")
public class Example2Controller extends BaseController {

    @Autowired
    private IExample2Service example2Service;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:example2:index")
    @GetMapping("/index")
    public JsonResult index(Example2Query query) {
        return example2Service.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "演示案例二", logType = LogType.INSERT)
    @RequiresPermissions("sys:example2:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody Example2 entity) {
        return example2Service.edit(entity);
    }

    /**
     * 获取详情
     *
     * @param example2Id 记录ID
     * @return
     */
    @GetMapping("/info/{example2Id}")
    public JsonResult info(@PathVariable("example2Id") Integer example2Id) {
        return example2Service.info(example2Id);
    }

    /**
     * 更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "演示案例二", logType = LogType.UPDATE)
    @RequiresPermissions("sys:example2:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody Example2 entity) {
        return example2Service.edit(entity);
    }

    /**
     * 删除记录
     *
     * @param example2Ids 记录ID
     * @return
     */
    @Log(title = "演示案例二", logType = LogType.DELETE)
    @RequiresPermissions("sys:example2:delete")
    @DeleteMapping("/delete/{example2Ids}")
    public JsonResult delete(@PathVariable("example2Ids") Integer[] example2Ids) {
        return example2Service.deleteByIds(example2Ids);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "演示案例二", logType = LogType.STATUS)
    @RequiresPermissions("sys:example2:status")
    @PutMapping("/setStatus")
    public JsonResult setStatus(@RequestBody Example2 entity) {
        return example2Service.setStatus(entity);
    }
}