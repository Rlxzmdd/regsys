package have.somuch.regsys.system.controller;


import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.entity.DictData;
import have.somuch.regsys.system.query.DictDataQuery;
import have.somuch.regsys.system.service.IDictDataService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 字典项管理表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-01
 */
@RestController
@RequestMapping("/dictdata")
public class DictDataController extends BaseController {

    @Autowired
    private IDictDataService dictDataService;

    /**
     * 获取字典项列表
     *
     * @param dictDataQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:dictionary:index")
    @GetMapping("/index")
    public JsonResult index(DictDataQuery dictDataQuery) {
        return dictDataService.getList(dictDataQuery);
    }

    /**
     * 添加字典项
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "字典管理", logType = LogType.INSERT)
    @RequiresPermissions("sys:dictionary:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody DictData entity) {
        return dictDataService.edit(entity);
    }

    /**
     * 编辑字典项
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "字典管理", logType = LogType.UPDATE)
    @RequiresPermissions("sys:dictionary:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody DictData entity) {
        return dictDataService.edit(entity);
    }

    /**
     * 删除字典项
     *
     * @param dicIds 字典项ID
     * @return
     */
    @Log(title = "字典管理", logType = LogType.DELETE)
    @RequiresPermissions("sys:dictionary:delete")
    @DeleteMapping("/delete/{dicIds}")
    public JsonResult delete(@PathVariable("dicIds") Integer[] dicIds) {
        return dictDataService.deleteByIds(dicIds);
    }

}
