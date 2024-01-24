package have.somuch.regsys.system.controller;


import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.entity.Ad;
import have.somuch.regsys.system.query.AdQuery;
import have.somuch.regsys.system.service.IAdService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 广告管理表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
@RestController
@RequestMapping("/ad")
public class AdController extends BaseController {

    @Autowired
    private IAdService adService;

    /**
     * 获取广告列表
     *
     * @param adQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:ad:index")
    @GetMapping("/index")
    public JsonResult index(AdQuery adQuery) {
        return adService.getList(adQuery);
    }

    /**
     * 添加广告
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "广告管理", logType = LogType.INSERT)
    @RequiresPermissions("sys:ad:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody Ad entity) {
        return adService.edit(entity);
    }

    /**
     * 编辑广告
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "广告管理", logType = LogType.UPDATE)
    @RequiresPermissions("sys:ad:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody Ad entity) {
        return adService.edit(entity);
    }

    /**
     * 删除广告
     *
     * @param adIds 广告ID
     * @return
     */
    @Log(title = "广告管理", logType = LogType.DELETE)
    @RequiresPermissions("sys:ad:delete")
    @DeleteMapping("/delete/{adIds}")
    public JsonResult delete(@PathVariable("adIds") Integer[] adIds) {
        return adService.deleteByIds(adIds);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "广告管理", logType = LogType.STATUS)
    @RequiresPermissions("sys:ad:status")
    @PutMapping("/status")
    public JsonResult status(@RequestBody Ad entity) {
        return adService.setStatus(entity);
    }

}
