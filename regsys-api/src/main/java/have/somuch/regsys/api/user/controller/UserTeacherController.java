package have.somuch.regsys.api.user.controller;

import have.somuch.regsys.api.user.query.UserTeacherQuery;
import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.api.user.entity.UserTeacher;
import have.somuch.regsys.api.user.service.IUserTeacherService;
import annotation.have.somuch.regsys.common.Log;
import utils.have.somuch.regsys.common.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author isZhous
 * @since 2024-01-27
 */
@RestController
@RequestMapping("/userteacher")
public class UserTeacherController extends BaseController {

    @Autowired
    private IUserTeacherService userTeacherService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:userteacher:index")
    @GetMapping("/index")
    public JsonResult index(UserTeacherQuery query) {
        return userTeacherService.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "", logType = LogType.INSERT)
    @RequiresPermissions("sys:userteacher:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody UserTeacher entity) {
        return userTeacherService.edit(entity);
    }

    /**
     * 获取详情
     *
     * @param userteacherId 记录ID
     * @return
     */
    @GetMapping("/info/{userteacherId}")
    public JsonResult info(@PathVariable("userteacherId") Integer userteacherId) {
        return userTeacherService.info(userteacherId);
    }

    /**
     * 更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "", logType = LogType.UPDATE)
    @RequiresPermissions("sys:userteacher:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody UserTeacher entity) {
        return userTeacherService.edit(entity);
    }

    /**
     * 删除记录
     *
     * @param userteacherIds 记录ID
     * @return
     */
    @Log(title = "", logType = LogType.DELETE)
    @RequiresPermissions("sys:userteacher:delete")
    @DeleteMapping("/delete/{userteacherIds}")
    public JsonResult delete(@PathVariable("userteacherIds") Integer[] userteacherIds) {
        return userTeacherService.deleteByIds(userteacherIds);
    }

}