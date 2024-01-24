package have.somuch.regsys.system.controller;


import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.config.CommonConfig;
import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.dto.ResetPwdDto;
import have.somuch.regsys.system.entity.User;
import have.somuch.regsys.system.query.UserQuery;
import have.somuch.regsys.system.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 后台用户管理表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-30
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 获取用户列表
     *
     * @param userQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:user:index")
    @GetMapping("/index")
    public JsonResult index(UserQuery userQuery) {
        return userService.getList(userQuery);
    }

    /**
     * 获取用户详情
     *
     * @param userId 用户ID
     * @return
     */
//    @RequiresPermissions("sys:user:detail")
    @GetMapping("/info/{userId}")
    public JsonResult info(@PathVariable("userId") Integer userId) {
        return userService.info(userId);
    }

    /**
     * 添加用户
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "用户管理", logType = LogType.INSERT)
    @RequiresPermissions("sys:user:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody User entity) {
        return userService.edit(entity);
    }

    /**
     * 编辑用户
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "用户管理", logType = LogType.UPDATE)
    @RequiresPermissions("sys:user:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody User entity) {
        return userService.edit(entity);
    }

    /**
     * 删除用户
     *
     * @param userIds 用户ID
     * @return
     */
    @Log(title = "用户管理", logType = LogType.DELETE)
    @RequiresPermissions("sys:user:delete")
    @DeleteMapping("/delete/{userIds}")
    public JsonResult delete(@PathVariable("userIds") Integer[] userIds) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        return userService.deleteByIds(userIds);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "用户管理", logType = LogType.STATUS)
    @RequiresPermissions("sys:user:status")
    @PutMapping("/status")
    public JsonResult status(@RequestBody User entity) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        return userService.setStatus(entity);
    }

    /**
     * 重置密码
     *
     * @param resetPwdDto 参数
     * @return
     */
    @Log(title = "用户管理", logType = LogType.RESETPWD)
    @RequiresPermissions("sys:user:resetPwd")
    @PutMapping("/resetPwd")
    public JsonResult resetPwd(@RequestBody ResetPwdDto resetPwdDto) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        return userService.resetPwd(resetPwdDto);
    }

}
