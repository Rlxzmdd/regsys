package have.somuch.regsys.system.controller;


import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.query.LoginLogQuery;
import have.somuch.regsys.system.service.ILoginLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 登录日志表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-31
 */
@RestController
@RequestMapping("/loginlog")
public class LoginLogController extends BaseController {

    @Autowired
    private ILoginLogService loginLogService;

    /**
     * 获取登录日志列表
     *
     * @param loginLogQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:loginlog:index")
    @GetMapping("/index")
    public JsonResult index(LoginLogQuery loginLogQuery) {
        return loginLogService.getList(loginLogQuery);
    }

    /**
     * 删除登录日志
     *
     * @param loginLogIds 登录日志ID
     * @return
     */
    @Log(title = "登录日志", logType = LogType.DELETE)
    @RequiresPermissions("sys:loginlog:delete")
    @DeleteMapping("/delete/{loginLogIds}")
    public JsonResult delete(@PathVariable("loginLogIds") Integer[] loginLogIds) {
        return loginLogService.deleteByIds(loginLogIds);
    }

}
