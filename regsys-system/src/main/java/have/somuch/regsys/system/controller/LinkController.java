package have.somuch.regsys.system.controller;


import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.entity.Link;
import have.somuch.regsys.system.query.LinkQuery;
import have.somuch.regsys.system.service.ILinkService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 友链管理表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
@RestController
@RequestMapping("/link")
public class LinkController extends BaseController {

    @Autowired
    private ILinkService linkService;

    /**
     * 获取友链列表
     *
     * @param linkQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:link:index")
    @GetMapping("/index")
    public JsonResult index(LinkQuery linkQuery) {
        return linkService.getList(linkQuery);
    }

    /**
     * 添加友链
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "友链管理", logType = LogType.INSERT)
    @RequiresPermissions("sys:link:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody Link entity) {
        return linkService.edit(entity);
    }

    /**
     * 编辑友链
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "友链管理", logType = LogType.UPDATE)
    @RequiresPermissions("sys:link:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody Link entity) {
        return linkService.edit(entity);
    }

    /**
     * 删除友链
     *
     * @param linkIds 友链ID
     * @return
     */
    @Log(title = "友链管理", logType = LogType.DELETE)
    @RequiresPermissions("sys:link:delete")
    @DeleteMapping("/delete/{linkIds}")
    public JsonResult delete(@PathVariable("linkIds") Integer[] linkIds) {
        return linkService.deleteByIds(linkIds);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "友链管理", logType = LogType.STATUS)
    @RequiresPermissions("sys:link:status")
    @PutMapping("/status")
    public JsonResult status(@RequestBody Link entity) {
        return linkService.setStatus(entity);
    }

}
