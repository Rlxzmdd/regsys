package have.somuch.regsys.api.user.controller;

import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.api.user.entity.UserAccount;
import have.somuch.regsys.api.user.query.UserAccountQuery;
import have.somuch.regsys.api.user.service.IUserAccountService;
import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.utils.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户账号信息表 前端控制器
 * </p>
 *
 * @author isZhous
 * @since 2024-01-29
 */
@RestController
@RequestMapping("/useraccount")
public class UserAccountController extends BaseController {

    @Autowired
    private IUserAccountService userAccountService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:useraccount:index")
    @GetMapping("/index")
    public JsonResult index(UserAccountQuery query) {
        return userAccountService.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "用户账号信息表", logType = LogType.INSERT)
    @RequiresPermissions("sys:useraccount:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody UserAccount entity) {
        return userAccountService.edit(entity);
    }

    /**
     * 获取详情
     *
     * @param useraccountId 记录ID
     * @return
     */
    @GetMapping("/info/{useraccountId}")
    public JsonResult info(@PathVariable("useraccountId") Integer useraccountId) {
        return userAccountService.info(useraccountId);
    }

    /**
     * 更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "用户账号信息表", logType = LogType.UPDATE)
    @RequiresPermissions("sys:useraccount:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody UserAccount entity) {
        return userAccountService.edit(entity);
    }

    /**
     * 删除记录
     *
     * @param useraccountIds 记录ID
     * @return
     */
    @Log(title = "用户账号信息表", logType = LogType.DELETE)
    @RequiresPermissions("sys:useraccount:delete")
    @DeleteMapping("/delete/{useraccountIds}")
    public JsonResult delete(@PathVariable("useraccountIds") Integer[] useraccountIds) {
        return userAccountService.deleteByIds(useraccountIds);
    }

}