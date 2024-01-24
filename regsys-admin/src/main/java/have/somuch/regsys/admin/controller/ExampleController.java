package have.somuch.regsys.admin.controller;

import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.admin.entity.Example;
import have.somuch.regsys.admin.query.ExampleQuery;
import have.somuch.regsys.admin.service.IExampleService;
import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.utils.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 演示案例一 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2024-01-24
 */
@RestController
@RequestMapping("/example")
public class ExampleController extends BaseController {

    @Autowired
    private IExampleService exampleService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:example:index")
    @GetMapping("/index")
    public JsonResult index(ExampleQuery query) {
        return exampleService.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "演示案例一", logType = LogType.INSERT)
    @RequiresPermissions("sys:example:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody Example entity) {
        return exampleService.edit(entity);
    }

    /**
     * 获取详情
     *
     * @param exampleId 记录ID
     * @return
     */
    @GetMapping("/info/{exampleId}")
    public JsonResult info(@PathVariable("exampleId") Integer exampleId) {
        return exampleService.info(exampleId);
    }

    /**
     * 更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "演示案例一", logType = LogType.UPDATE)
    @RequiresPermissions("sys:example:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody Example entity) {
        return exampleService.edit(entity);
    }

    /**
     * 删除记录
     *
     * @param exampleIds 记录ID
     * @return
     */
    @Log(title = "演示案例一", logType = LogType.DELETE)
    @RequiresPermissions("sys:example:delete")
    @DeleteMapping("/delete/{exampleIds}")
    public JsonResult delete(@PathVariable("exampleIds") Integer[] exampleIds) {
        return exampleService.deleteByIds(exampleIds);
    }

    /**
     * 设置是否VIP
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "演示案例一", logType = LogType.STATUS)
    @RequiresPermissions("sys:example:isVip")
    @PutMapping("/setIsVip")
    public JsonResult setIsVip(@RequestBody Example entity) {
        return exampleService.setIsVip(entity);
    }
    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "演示案例一", logType = LogType.STATUS)
    @RequiresPermissions("sys:example:status")
    @PutMapping("/setStatus")
    public JsonResult setStatus(@RequestBody Example entity) {
        return exampleService.setStatus(entity);
    }
}