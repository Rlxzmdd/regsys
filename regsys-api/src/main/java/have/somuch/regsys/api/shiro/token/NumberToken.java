package have.somuch.regsys.api.shiro.token;


import have.somuch.regsys.api.common.constant.LoginTypeEnum;
import have.somuch.regsys.api.user.dto.WechatLoginDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * 小程序学号+密码登录
 * 交由subject login选择Realm进行身份校验
 *
 * @author iszhous
 */
@Builder
@AllArgsConstructor
public class NumberToken implements AuthenticationToken {

    private WechatLoginDto dto;

    @Override
    public Object getPrincipal() {
        return dto.getNumber();
    }

    @Override
    public Object getCredentials() {
        return dto.getNumber();
    }

    public LoginTypeEnum getType() {
        return dto.getType();
    }

}
