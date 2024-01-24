package have.somuch.regsys.system.controller;


import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.entity.Position;
import have.somuch.regsys.system.query.PositionQuery;
import have.somuch.regsys.system.service.IPositionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 岗位表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-02
 */
@RestController
@RequestMapping("/position")
public class PositionController extends BaseController {

    @Autowired
    private IPositionService positionService;

    /**
     * 获取岗位列表
     *
     * @param positionQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:position:index")
    @GetMapping("/index")
    public JsonResult index(PositionQuery positionQuery) {
        return positionService.getList(positionQuery);
    }

    /**
     * 添加岗位
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "岗位管理", logType = LogType.INSERT)
    @RequiresPermissions("sys:position:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody Position entity) {
        return positionService.edit(entity);
    }

    /**
     * 编辑岗位
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "岗位管理", logType = LogType.UPDATE)
    @RequiresPermissions("sys:position:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody Position entity) {
        return positionService.edit(entity);
    }

    /**
     * 删除岗位
     *
     * @param positionIds 岗位ID
     * @return
     */
    @Log(title = "岗位管理", logType = LogType.DELETE)
    @RequiresPermissions("sys:position:delete")
    @DeleteMapping("/delete/{positionIds}")
    public JsonResult delete(@PathVariable("positionIds") Integer[] positionIds) {
        return positionService.deleteByIds(positionIds);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "岗位管理", logType = LogType.STATUS)
    @RequiresPermissions("sys:position:status")
    @PutMapping("/status")
    public JsonResult status(@RequestBody Position entity) {
        return positionService.setStatus(entity);
    }

    /**
     * 获取岗位列表
     *
     * @return
     */
    @GetMapping("/getPositionList")
    public JsonResult getPositionList() {
        return positionService.getPositionList();
    }

}
