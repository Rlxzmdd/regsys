package have.somuch.regsys.system.controller;


import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.entity.Config;
import have.somuch.regsys.system.query.ConfigQuery;
import have.somuch.regsys.system.service.IConfigService;
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
@RequestMapping("/config")
public class ConfigController extends BaseController {

    @Autowired
    private IConfigService configService;

    /**
     * 获取配置列表
     *
     * @param configQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:config:index")
    @GetMapping("/index")
    public JsonResult index(ConfigQuery configQuery) {
        return configService.getList(configQuery);
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
    public JsonResult add(@RequestBody Config entity) {
        return configService.edit(entity);
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
    public JsonResult edit(@RequestBody Config entity) {
        return configService.edit(entity);
    }

    /**
     * 删除配置
     *
     * @param configId 配置ID
     * @return
     */
    @Log(title = "配置管理", logType = LogType.DELETE)
    @RequiresPermissions("sys:config:delete")
    @DeleteMapping("/delete/{configId}")
    public JsonResult delete(@PathVariable("configId") Integer configId) {
        return configService.deleteById(configId);
    }

}
