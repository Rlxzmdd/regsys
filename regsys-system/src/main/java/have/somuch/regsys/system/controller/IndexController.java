package have.somuch.regsys.system.controller;


import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.config.CommonConfig;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.dto.UpdatePwdDto;
import have.somuch.regsys.system.dto.UpdateUserInfoDto;
import have.somuch.regsys.system.entity.Menu;
import have.somuch.regsys.system.service.IMenuService;
import have.somuch.regsys.system.service.IUserService;
import have.somuch.regsys.system.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统主页 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-30
 */
@RestController
@RequestMapping("/index")
public class IndexController extends BaseController {

    @Autowired
    private IMenuService menuService;
    @Autowired
    private IUserService userService;

    /**
     * 获取导航菜单
     *
     * @return
     */
    @GetMapping("/getMenuList")
    public JsonResult getMenuList() {
        List<Menu> menuList = menuService.getMenuList(ShiroUtils.getUserId());
        return JsonResult.success(menuList);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping("/getUserInfo")
    public JsonResult getUserInfo() {
        return userService.getUserInfo();
    }

    /**
     * 修改密码
     *
     * @param updatePwdDto 参数
     * @return
     */
    @PutMapping("/updatePwd")
    public JsonResult updatePwd(@RequestBody UpdatePwdDto updatePwdDto) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        return userService.updatePwd(updatePwdDto);
    }

    /**
     * 更新个人资料
     *
     * @param updateUserInfoDto 参数
     * @return
     */
    @PutMapping("/updateUserInfo")
    public JsonResult updateUserInfo(@RequestBody UpdateUserInfoDto updateUserInfoDto) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        return userService.updateUserInfo(updateUserInfoDto);
    }
}
