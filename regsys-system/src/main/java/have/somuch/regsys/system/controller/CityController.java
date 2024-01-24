package have.somuch.regsys.system.controller;


import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.entity.City;
import have.somuch.regsys.system.query.CityQuery;
import have.somuch.regsys.system.service.ICityService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 高德城市表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-03
 */
@RestController
@RequestMapping("/city")
public class CityController extends BaseController {

    @Autowired
    private ICityService cityService;

    /**
     * 获取城市列表
     *
     * @param cityQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:city:index")
    @GetMapping("/index")
    public JsonResult index(CityQuery cityQuery) {
        return cityService.getList(cityQuery);
    }

    /**
     * 添加城市
     *
     * @param entity 查询条件
     * @return
     */
    @Log(title = "城市管理", logType = LogType.INSERT)
    @RequiresPermissions("sys:city:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody City entity) {
        return cityService.add(entity);
    }

    /**
     * 编辑城市
     *
     * @param entity 查询条件
     * @return
     */
    @Log(title = "城市管理", logType = LogType.UPDATE)
    @RequiresPermissions("sys:city:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody City entity) {
        return cityService.edit(entity);
    }

    /**
     * 删除城市
     *
     * @param cityId 城市ID
     * @return
     */
    @Log(title = "城市管理", logType = LogType.DELETE)
    @RequiresPermissions("sys:city:delete")
    @DeleteMapping("/delete/{cityId}")
    public JsonResult delete(@PathVariable("cityId") Integer cityId) {
        return cityService.deleteById(cityId);
    }

}
