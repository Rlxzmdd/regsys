package have.somuch.regsys.system.controller;


import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.query.OperLogQuery;
import have.somuch.regsys.system.service.IOperLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 操作日志记录 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-31
 */
@RestController
@RequestMapping("/operlog")
public class OperLogController extends BaseController {

    @Autowired
    private IOperLogService operLogService;

    /**
     * 获取操作日志列表
     *
     * @param operLogQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:operlog:index")
    @GetMapping("/index")
    public JsonResult index(OperLogQuery operLogQuery) {
        return operLogService.getList(operLogQuery);
    }

    /**
     * 删除操作日志
     *
     * @param operLogId 操作日志ID
     * @return
     */
    @Log(title = "操作日志", logType = LogType.DELETE)
    @RequiresPermissions("sys:operlog:delete")
    @DeleteMapping("/delete/{operLogId}")
    public JsonResult delete(@PathVariable("operLogId") Integer operLogId) {
        return operLogService.deleteById(operLogId);
    }

}
