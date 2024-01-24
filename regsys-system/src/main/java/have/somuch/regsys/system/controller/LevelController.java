package have.somuch.regsys.system.controller;


import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.config.CommonConfig;
import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.utils.ExcelUtils;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.entity.Level;
import have.somuch.regsys.system.query.LevelQuery;
import have.somuch.regsys.system.service.ILevelService;
import have.somuch.regsys.system.vo.level.LevelInfoVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 职级表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-02
 */
@RestController
@RequestMapping("/level")
public class LevelController extends BaseController {

    @Autowired
    private ILevelService levelService;

    /**
     * 获取职级列表
     *
     * @param levelQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:level:index")
    @GetMapping("/index")
    public JsonResult index(LevelQuery levelQuery) {
        return levelService.getList(levelQuery);
    }

    /**
     * 添加职级
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "职级管理", logType = LogType.INSERT)
    @RequiresPermissions("sys:level:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody Level entity) {
        return levelService.edit(entity);
    }

    /**
     * 编辑职级
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "职级管理", logType = LogType.UPDATE)
    @RequiresPermissions("sys:level:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody Level entity) {
        return levelService.edit(entity);
    }

    /**
     * 删除职级
     *
     * @param levelIds 职级ID
     * @return
     */
    @Log(title = "职级管理", logType = LogType.DELETE)
    @RequiresPermissions("sys:level:delete")
    @DeleteMapping("/delete/{levelIds}")
    public JsonResult delete(@PathVariable("levelIds") Integer[] levelIds) {
        return levelService.deleteByIds(levelIds);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "职级管理", logType = LogType.STATUS)
    @RequiresPermissions("sys:level:status")
    @PutMapping("/status")
    public JsonResult status(@RequestBody Level entity) {
        return levelService.setStatus(entity);
    }

    /**
     * 获取职级列表
     *
     * @return
     */
    @GetMapping("/getLevelList")
    public JsonResult getLevelList() {
        return levelService.getLevelList();
    }

    /**
     * 导入Excel
     *
     * @param request 网络请求
     * @return
     */
    @Log(title = "职级管理", logType = LogType.IMPORT)
    @RequiresPermissions("sys:level:import")
    @PostMapping("/importExcel/{name}")
    public JsonResult importExcel(HttpServletRequest request, @PathVariable("name") String name) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        return levelService.importExcel(request, name);
    }

    /**
     * 导出Excel
     *
     * @param levelQuery 查询条件
     * @return
     */
    @PostMapping("/exportExcel")
    public JsonResult exportExcel(@RequestBody LevelQuery levelQuery) {
        List<LevelInfoVo> levelInfoVoList = levelService.exportExcel(levelQuery);
        ExcelUtils<LevelInfoVo> excelUtils = new ExcelUtils<LevelInfoVo>(LevelInfoVo.class);
        return excelUtils.exportExcel(levelInfoVoList, "职级列表");
    }

}
