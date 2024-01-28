package have.somuch.regsys.api.shiro.auth;

import have.somuch.regsys.api.shiro.principal.WechatProgramUserPrincipal;
import have.somuch.regsys.api.shiro.realm.JwtTokenRealm;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Collection;

/**
 * 自定义授权Realm，根据不同类型的Principal
 * 选择不同realm中的授权方法，与Authenticator 类似
 */
public class RealmAuthorizer extends ModularRealmAuthorizer {
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        assertRealmsConfigured();
        Object primaryPrincipal = principals.getPrimaryPrincipal();
        Collection<Realm> realms = getRealms();
        for (Realm realm : realms) {
            if (!(realm instanceof Authorizer)) {
                continue;
            }
            /*
            Principal 对象由Realm中的：doGetAuthenticationInfo 方法返回携带的Principal
            类型为User 类型时会被调用，返回isPermitted 将会调用Realm中的 doGetAuthorizationInfo
            的认证方法进行鉴权
             */
            if (primaryPrincipal instanceof WechatProgramUserPrincipal) {
                if (realm instanceof JwtTokenRealm) {
                    return ((JwtTokenRealm) realm).isPermitted(principals, permission);
                }
            }
        }
        return false;
    }
}
