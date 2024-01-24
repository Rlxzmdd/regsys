package have.somuch.regsys.system.controller;

import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.entity.Article;
import have.somuch.regsys.system.query.ArticleQuery;
import have.somuch.regsys.system.service.IArticleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 文章管理表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2021-10-11
 */
@RestController
@RequestMapping("/article")
public class ArticleController extends BaseController {

    @Autowired
    private IArticleService articleService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:article:index")
    @GetMapping("/index")
    public JsonResult index(ArticleQuery query) {
        return articleService.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "文章管理表", logType = LogType.INSERT)
    @RequiresPermissions("sys:article:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody Article entity) {
        return articleService.edit(entity);
    }

    /**
     * 获取详情
     *
     * @param articleId 记录ID
     * @return
     */
    @GetMapping("/info/{articleId}")
    public JsonResult info(@PathVariable("articleId") Integer articleId) {
        return articleService.info(articleId);
    }

    /**
     * 更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "文章管理表", logType = LogType.UPDATE)
    @RequiresPermissions("sys:article:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody Article entity) {
        return articleService.edit(entity);
    }

    /**
     * 删除记录
     *
     * @param articleIds 记录ID
     * @return
     */
    @Log(title = "文章管理表", logType = LogType.DELETE)
    @RequiresPermissions("sys:article:delete")
    @DeleteMapping("/delete/{articleIds}")
    public JsonResult delete(@PathVariable("articleIds") Integer[] articleIds) {
        return articleService.deleteByIds(articleIds);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "文章管理表", logType = LogType.STATUS)
    @RequiresPermissions("sys:article:status")
    @PutMapping("/status")
    public JsonResult setStatus(@RequestBody Article entity) {
        return articleService.setStatus(entity);
    }
}