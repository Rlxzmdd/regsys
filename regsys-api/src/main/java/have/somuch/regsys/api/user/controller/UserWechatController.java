package have.somuch.regsys.api.user.controller;

import have.somuch.regsys.api.common.dto.AuthToken2CredentialDto;
import have.somuch.regsys.api.common.utils.JwtUtil;
import have.somuch.regsys.api.user.dto.WechatLoginDto;
import have.somuch.regsys.api.user.entity.UserWechat;
import have.somuch.regsys.api.user.query.UserWechatQuery;
import have.somuch.regsys.api.user.service.IUserWechatService;
import have.somuch.regsys.api.common.utils.JsonResultS;
import have.somuch.regsys.common.annotation.Log;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.utils.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户微信表 前端控制器
 * </p>
 *
 * @author isZhous
 * @since 2024-01-25
 */
@RestController
@RequestMapping("/wechat")
public class UserWechatController extends BaseController {

    @Autowired
    private IUserWechatService userWechatService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:userwechat:index")
    @GetMapping("/index")
    public JsonResult index(UserWechatQuery query) {
        return userWechatService.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "用户微信表", logType = LogType.INSERT)
    @RequiresPermissions("sys:userwechat:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody UserWechat entity) {
        return userWechatService.edit(entity);
    }

    /**
     * 获取详情
     *
     * @param userWechatId 记录ID
     * @return
     */
    @GetMapping("/info/{userwechatId}")
    public JsonResult info(@PathVariable("userwechatId") Integer userWechatId) {
        return userWechatService.info(userWechatId);
    }

    /**
     * 更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "用户微信表", logType = LogType.UPDATE)
    @RequiresPermissions("sys:userwechat:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody UserWechat entity) {
        return userWechatService.edit(entity);
    }

    /**
     * 删除记录
     *
     * @param userwechatIds 记录ID
     * @return
     */
    @Log(title = "用户微信表", logType = LogType.DELETE)
    @RequiresPermissions("sys:userwechat:delete")
    @DeleteMapping("/delete/{userwechatIds}")
    public JsonResult delete(@PathVariable("userwechatIds") Integer[] userwechatIds) {
        return userWechatService.deleteByIds(userwechatIds);
    }

    /**
     * 小程序用户登录信息，同时绑定用户，
     *
     * @param code  小程序code
     * @param dto   通过springboot自动注入的请求头，详情见dto类
     * @return
     */
    @PostMapping("/login")
    public JsonResultS login(String code, @RequestHeader(HttpHeaders.AUTHORIZATION) WechatLoginDto dto) {
        return userWechatService.login(code, dto);
    }

    /**
     * 小程序检查绑定（快捷登录）
     *
     * @param code 小程序code
     * @return
     */
    @GetMapping("/obtain/{code}")
    public JsonResultS obtain(@PathVariable("code") String code) {
        return userWechatService.obtain(code);
    }

}