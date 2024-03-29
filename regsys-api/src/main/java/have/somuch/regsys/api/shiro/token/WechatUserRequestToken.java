package have.somuch.regsys.api.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author iszhous
 */
public class WechatUserRequestToken implements AuthenticationToken {

    /*登录Token*/
    private final String token;

    public WechatUserRequestToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }
}
