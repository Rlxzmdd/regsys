package have.somuch.regsys.system.controller;

import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.dto.LoginDto;
import have.somuch.regsys.system.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 系统登录 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-31
 */
@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Autowired
    private ILoginService loginService;

    /**
     * 获取验证码
     *
     * @param response 网络请求
     * @return
     */
    @GetMapping("/captcha")
    public JsonResult captcha(HttpServletResponse response) {
        return loginService.captcha(response);
    }

    /**
     * 系统登录
     *
     * @param loginDto 参数
     * @param request  网络请求
     * @return
     */
    @PostMapping("/login")
    public JsonResult login(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        return loginService.login(loginDto, request);
    }

    /**
     * 退出登录
     *
     * @return
     */
    @GetMapping("/logout")
    public JsonResult logout() {
        return loginService.logout();
    }

    /**
     * 用户未登录
     *
     * @return
     */
    @GetMapping("/un_auth")
    public JsonResult unAuth() {
        return JsonResult.error(HttpStatus.UNAUTHORIZED, "");
    }

    /**
     * 用户无权限
     *
     * @return
     */
    @GetMapping("/unauthorized")
    public JsonResult unauthorized() {
        return JsonResult.error(HttpStatus.FORBIDDEN, "用户无权限");
    }

}
