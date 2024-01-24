package have.somuch.regsys.system.controller;


import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.entity.ItemCate;
import have.somuch.regsys.system.query.ItemCateQuery;
import have.somuch.regsys.system.service.IItemCateService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 栏目管理表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
@RestController
@RequestMapping("/itemcate")
public class ItemCateController extends BaseController {

    @Autowired
    private IItemCateService itemCateService;

    /**
     * 获取栏目列表
     *
     * @param itemCateQuery 查询条件
     * @return
     */
    @Log(title = "栏目管理", logType = LogType.INSERT)
    @RequiresPermissions("sys:itemcate:index")
    @GetMapping("/index")
    public JsonResult index(ItemCateQuery itemCateQuery) {
        return itemCateService.getList(itemCateQuery);
    }

    /**
     * 添加栏目
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "栏目管理", logType = LogType.INSERT)
    @RequiresPermissions("sys:itemcate:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody ItemCate entity) {
        return itemCateService.edit(entity);
    }

    /**
     * 编辑栏目
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "栏目管理", logType = LogType.UPDATE)
    @RequiresPermissions("sys:itemcate:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody ItemCate entity) {
        return itemCateService.edit(entity);
    }

    /**
     * 删除栏目
     *
     * @param itemCateId 栏目ID
     * @return
     */
    @Log(title = "栏目管理", logType = LogType.DELETE)
    @RequiresPermissions("sys:itemcate:delete")
    @DeleteMapping("/delete/{itemCateId}")
    public JsonResult delete(@PathVariable("itemCateId") Integer itemCateId) {
        return itemCateService.deleteById(itemCateId);
    }

    /**
     * 获取栏目列表
     *
     * @return
     */
    @GetMapping("/getCateList")
    public JsonResult getCateList() {
        return JsonResult.success(itemCateService.getCateList());
    }

}
