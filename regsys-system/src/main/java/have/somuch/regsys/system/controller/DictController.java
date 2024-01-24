package have.somuch.regsys.system.controller;


import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.entity.Dict;
import have.somuch.regsys.system.query.DictQuery;
import have.somuch.regsys.system.service.IDictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 字典类型表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-01
 */
@RestController
@RequestMapping("/dict")
public class DictController extends BaseController {

    @Autowired
    private IDictService dictService;

    /**
     * 获取字典列表
     *
     * @param dictQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:dictionary:index")
    @GetMapping("/index")
    public JsonResult index(DictQuery dictQuery) {
        return dictService.getList(dictQuery);
    }

    /**
     * 添加字典
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "字典分组", logType = LogType.INSERT)
    @RequiresPermissions("sys:dictionary:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody Dict entity) {
        return dictService.edit(entity);
    }

    /**
     * 编辑字典
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "字典分组", logType = LogType.UPDATE)
    @RequiresPermissions("sys:dictionary:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody Dict entity) {
        return dictService.edit(entity);
    }

    /**
     * 删除字典
     *
     * @param dicTypeId 字典ID
     * @return
     */
    @Log(title = "字典分组", logType = LogType.DELETE)
    @RequiresPermissions("sys:dictionary:delete")
    @DeleteMapping("/delete/{dicTypeId}")
    public JsonResult delete(@PathVariable("dicTypeId") Integer dicTypeId) {
        return dictService.deleteById(dicTypeId);
    }

}
