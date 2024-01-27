package have.somuch.regsys.api.user.controller;

import have.somuch.regsys.api.user.query.UserStudentQuery;
import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.api.user.entity.UserStudent;
import have.somuch.regsys.api.user.service.IUserStudentService;
import annotation.have.somuch.regsys.common.Log;
import utils.have.somuch.regsys.common.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 学生信息表 前端控制器
 * </p>
 *
 * @author isZhous
 * @since 2024-01-27
 */
@RestController
@RequestMapping("/userstudent")
public class UserStudentController extends BaseController {

    @Autowired
    private IUserStudentService userStudentService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:userstudent:index")
    @GetMapping("/index")
    public JsonResult index(UserStudentQuery query) {
        return userStudentService.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "学生信息表", logType = LogType.INSERT)
    @RequiresPermissions("sys:userstudent:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody UserStudent entity) {
        return userStudentService.edit(entity);
    }

    /**
     * 获取详情
     *
     * @param userstudentId 记录ID
     * @return
     */
    @GetMapping("/info/{userstudentId}")
    public JsonResult info(@PathVariable("userstudentId") Integer userstudentId) {
        return userStudentService.info(userstudentId);
    }

    /**
     * 更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "学生信息表", logType = LogType.UPDATE)
    @RequiresPermissions("sys:userstudent:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody UserStudent entity) {
        return userStudentService.edit(entity);
    }

    /**
     * 删除记录
     *
     * @param userstudentIds 记录ID
     * @return
     */
    @Log(title = "学生信息表", logType = LogType.DELETE)
    @RequiresPermissions("sys:userstudent:delete")
    @DeleteMapping("/delete/{userstudentIds}")
    public JsonResult delete(@PathVariable("userstudentIds") Integer[] userstudentIds) {
        return userStudentService.deleteByIds(userstudentIds);
    }

}