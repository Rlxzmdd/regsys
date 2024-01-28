package have.somuch.regsys.api.shiro.matcher;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 自定义JWT 密码校验器
 * JWT 无需密码对比只需传递的鉴权对象不为NULL 即可
 */
public class JwtCredentialsMatcher extends SimpleCredentialsMatcher {

    /**
     * 校验Token
     * 判断是否被JwtTokenRealm 传递，无需再对Token进行合法性验证，验证已在Realm中完成
     *
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        return token != null && getCredentials(info) != null;
    }
}
