package have.somuch.regsys.system.controller;

import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.config.CommonConfig;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.service.IConfigWebService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/configweb")
public class ConfigWebController extends BaseController {

    @Autowired
    private IConfigWebService configWebService;

    /**
     * 获取配置列表
     *
     * @return
     */
    @RequiresPermissions("sys:website:index")
    @GetMapping("/index")
    public JsonResult index() {
        return configWebService.getList();
    }

    /**
     * 保存配置信息
     *
     * @param info 表单信息
     * @return
     */
    @RequiresPermissions("sys:website:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody Map<String, Object> info) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        return configWebService.edit(info);
    }

}
