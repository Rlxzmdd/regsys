package have.somuch.regsys.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.dto.LoginDto;
import have.somuch.regsys.system.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 后台用户管理表 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-02-26
 */
public interface ILoginService extends IService<User> {

    /**
     * 获取验证码
     *
     * @param response 请求响应
     * @return
     */
    JsonResult captcha(HttpServletResponse response);

    /**
     * 用户登录
     *
     * @param loginDto 登录Dto
     * @return
     */
    JsonResult login(LoginDto loginDto, HttpServletRequest request);

    /**
     * 退出登录
     *
     * @return
     */
    JsonResult logout();

    /**
     * 根据名称获取对象
     *
     * @param username 名称
     * @return
     */
    User getUserByName(String username);

    /**
     * 系统登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    User login(String username, String password);

}
