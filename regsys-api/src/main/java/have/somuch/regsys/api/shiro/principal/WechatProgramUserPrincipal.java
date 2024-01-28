package have.somuch.regsys.api.shiro.principal;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Realm 完成验证传递的凭据对象
 * 由JWTFilter生成并传递给Realm
 * Authenticator 根据凭据类型选择目标授权方法的Realm
 */
@Data
@Accessors(chain = true)
public class WechatProgramUserPrincipal {
    private String id;

    @Setter(AccessLevel.NONE)
    private String number;

    private String type;

    public WechatProgramUserPrincipal setNumber(String number) {
        this.number = number;
        this.id = number;
        return this;
    }
}