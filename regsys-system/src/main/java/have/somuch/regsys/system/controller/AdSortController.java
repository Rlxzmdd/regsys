package have.somuch.regsys.system.controller;


import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.entity.AdSort;
import have.somuch.regsys.system.query.AdSortQuery;
import have.somuch.regsys.system.service.IAdSortService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 广告位管理表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
@RestController
@RequestMapping("/adsort")
public class AdSortController extends BaseController {

    @Autowired
    private IAdSortService adSortService;

    /**
     * 获取广告位列表
     *
     * @param adSortQuery 查询条件
     * @return
     */
    @Log(title = "广告位管理", logType = LogType.INSERT)
    @RequiresPermissions("sys:adsort:index")
    @GetMapping("/index")
    public JsonResult index(AdSortQuery adSortQuery) {
        return adSortService.getList(adSortQuery);
    }

    /**
     * 添加广告位
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "广告位管理", logType = LogType.INSERT)
    @RequiresPermissions("sys:adsort:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody AdSort entity) {
        return adSortService.edit(entity);
    }

    /**
     * 编辑广告位
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "广告位管理", logType = LogType.UPDATE)
    @RequiresPermissions("sys:adsort:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody AdSort entity) {
        return adSortService.edit(entity);
    }

    /**
     * 删除广告位
     *
     * @param adSortIds 广告位ID
     * @return
     */
    @Log(title = "广告位管理", logType = LogType.DELETE)
    @RequiresPermissions("sys:adsort:delete")
    @DeleteMapping("/delete/{adSortIds}")
    public JsonResult delete(@PathVariable("adSortIds") Integer[] adSortIds) {
        return adSortService.deleteByIds(adSortIds);
    }

    /**
     * 获取广告位列表
     *
     * @return
     */
    @GetMapping("/getAdSortList")
    public JsonResult getAdSortList() {
        return adSortService.getAdSortList();
    }

}
