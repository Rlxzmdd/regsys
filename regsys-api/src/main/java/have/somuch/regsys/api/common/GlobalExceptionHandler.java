package have.somuch.regsys.api.common;

import have.somuch.regsys.api.common.utils.JsonResultS;
import org.apache.shiro.authc.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 * @author iszhous
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /* 主要为登录相关错误 */
    @ExceptionHandler(value = UnknownAccountException.class)
    @ResponseBody
    public JsonResultS handleUnknownAccountException(UnknownAccountException e) {
        return JsonResultS.error("未知账号异常");
    }

    @ExceptionHandler(value = IncorrectCredentialsException.class)
    @ResponseBody
    public JsonResultS handleIncorrectCredentialsException(IncorrectCredentialsException e) {
        return JsonResultS.error("凭证错误异常");
    }

    @ExceptionHandler(value = LockedAccountException.class)
    @ResponseBody
    public JsonResultS handleLockedAccountException(LockedAccountException e) {
        return JsonResultS.error("账号已锁定异常");
    }

    @ExceptionHandler(value = ExcessiveAttemptsException.class)
    @ResponseBody
    public JsonResultS handleExcessiveAttemptsException(ExcessiveAttemptsException e) {
        return JsonResultS.error("尝试次数过多异常");
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    public JsonResultS handleAuthenticationException(AuthenticationException e) {
        return JsonResultS.error("认证异常");
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResultS handleException(Exception e) {
        return JsonResultS.error(String.format("未知异常: %s", e.getMessage()));
    }
}