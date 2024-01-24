package have.somuch.regsys.admin.controller;


import have.somuch.regsys.admin.entity.Member;
import have.somuch.regsys.admin.query.MemberQuery;
import have.somuch.regsys.admin.service.IMemberService;
import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.utils.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-04
 */
@RestController
@RequestMapping("/member")
public class MemberController extends BaseController {

    @Autowired
    private IMemberService memberService;

    /**
     * 获取会员列表
     *
     * @param memberQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:member:index")
    @GetMapping("/index")
    public JsonResult index(MemberQuery memberQuery) {
        return memberService.getList(memberQuery);
    }

    /**
     * 添加会员
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "会员管理", logType = LogType.INSERT)
    @RequiresPermissions("sys:member:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody Member entity) {
        return memberService.edit(entity);
    }

    /**
     * 编辑会员
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "会员管理", logType = LogType.UPDATE)
    @RequiresPermissions("sys:member:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody Member entity) {
        return memberService.edit(entity);
    }

    /**
     * 删除会员
     *
     * @param memberIds 会员ID
     * @return
     */
    @Log(title = "会员管理", logType = LogType.DELETE)
    @RequiresPermissions("sys:member:delete")
    @DeleteMapping("/delete/{memberIds}")
    public JsonResult delete(@PathVariable("memberIds") Integer[] memberIds) {
        return memberService.deleteByIds(memberIds);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "会员管理", logType = LogType.STATUS)
    @RequiresPermissions("sys:member:status")
    @PutMapping("/status")
    public JsonResult status(@RequestBody Member entity) {
        return memberService.setStatus(entity);
    }

}
