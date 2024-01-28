package have.somuch.regsys.api.shiro.token;


import have.somuch.regsys.api.user.dto.WechatStudentLoginDto;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * 小程序学号+姓名登陆凭据
 * 交由subject login选择Realm进行身份校验
 */
public class WechatStudentUserToken implements AuthenticationToken {

    /*认证实体*/
    private WechatStudentLoginDto token;

    public WechatStudentUserToken(WechatStudentLoginDto token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return this.token.getStuNumber();
    }

    @Override
    public Object getCredentials() {
        return this.token.getRealName();
    }

}
