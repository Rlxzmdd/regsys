package have.somuch.regsys.system.controller;


import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.entity.Notice;
import have.somuch.regsys.system.query.NoticeQuery;
import have.somuch.regsys.system.service.INoticeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 通知公告表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
@RestController
@RequestMapping("/notice")
public class NoticeController extends BaseController {

    @Autowired
    private INoticeService noticeService;

    /**
     * 获取通知公告列表
     *
     * @param noticeQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:notice:index")
    @GetMapping("/index")
    public JsonResult index(NoticeQuery noticeQuery) {
        return noticeService.getList(noticeQuery);
    }

    /**
     * 获取通知公告详情
     *
     * @param noticeId 通知公告ID
     * @return
     */
    @GetMapping("/info/{noticeId}")
    public JsonResult info(@PathVariable("noticeId") Integer noticeId) {
        return noticeService.info(noticeId);
    }

    /**
     * 添加通知公告
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "通知公告", logType = LogType.INSERT)
    @RequiresPermissions("sys:notice:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody Notice entity) {
        return noticeService.edit(entity);
    }

    /**
     * 编辑通知公告
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "通知公告", logType = LogType.UPDATE)
    @RequiresPermissions("sys:notice:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody Notice entity) {
        return noticeService.edit(entity);
    }

    /**
     * 删除通知公告
     *
     * @param noticeIds 通知公告ID
     * @return
     */
    @Log(title = "通知公告", logType = LogType.DELETE)
    @RequiresPermissions("sys:notice:delete")
    @DeleteMapping("/delete/{noticeIds}")
    public JsonResult delete(@PathVariable("noticeIds") Integer[] noticeIds) {
        return noticeService.deleteByIds(noticeIds);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "通知公告", logType = LogType.STATUS)
    @RequiresPermissions("sys:notice:status")
    @PutMapping("/status")
    public JsonResult status(@RequestBody Notice entity) {
        return noticeService.setStatus(entity);
    }

    /**
     * 设置指定
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:notice:setIsTop")
    @PutMapping("/setIsTop")
    public JsonResult setIsTop(@RequestBody Notice entity) {
        return noticeService.setIsTop(entity);
    }

}
