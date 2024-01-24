package have.somuch.regsys.system.controller;


import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.entity.ConfigData;
import have.somuch.regsys.system.query.ConfigDataQuery;
import have.somuch.regsys.system.service.IConfigDataService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 配置表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-06
 */
@RestController
@RequestMapping("/configdata")
public class ConfigDataController extends BaseController {

    @Autowired
    private IConfigDataService configDataService;

    /**
     * 获取配置列表
     *
     * @param configDataQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:config:index")
    @GetMapping("/index")
    public JsonResult index(ConfigDataQuery configDataQuery) {
        return configDataService.getList(configDataQuery);
    }

    /**
     * 添加配置
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "配置管理", logType = LogType.INSERT)
    @RequiresPermissions("sys:config:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody ConfigData entity) {
        return configDataService.edit(entity);
    }

    /**
     * 编辑配置
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "配置管理", logType = LogType.UPDATE)
    @RequiresPermissions("sys:config:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody ConfigData entity) {
        return configDataService.edit(entity);
    }

    /**
     * 删除配置
     *
     * @param configIds 配置ID
     * @return
     */
    @Log(title = "配置管理", logType = LogType.DELETE)
    @RequiresPermissions("sys:config:delete")
    @DeleteMapping("/delete/{configIds}")
    public JsonResult delete(@PathVariable("configIds") Integer[] configIds) {
        return configDataService.deleteByIds(configIds);
    }

    /**
     * 更新状态
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "配置管理", logType = LogType.STATUS)
    @RequiresPermissions("sys:config:status")
    @PutMapping("/status")
    public JsonResult status(@RequestBody ConfigData entity) {
        return configDataService.setStatus(entity);
    }

}
