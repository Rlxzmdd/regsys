package have.somuch.regsys.api.shiro.auth;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.realm.Realm;

import java.util.Collection;

/**
 * 自定义身份认证器 Realm ，根据构造的Token类型自动分配到对应的Realm中
 * 由Realm support方法判断其适用性
 */
public class CustomModularRealmAuthenticator extends ModularRealmAuthenticator {
    /**
     * 自定义Realm 适配器，寻找可以处理token的Realm
     *
     * @param realms Realm认证器集合
     * @param token  登录Token
     * @return Realm 可处理Token的Realm
     */
    @Override
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) {
        // 判断getRealms()是否返回为空
        assertRealmsConfigured();
        Realm uniqueRealm = null;
        for (Realm realm : realms) {
            if (realm.supports(token)) {
                uniqueRealm = realm;
                break;
            }
        }
        if (uniqueRealm == null) {
            throw new UnsupportedTokenException();
        }
        return uniqueRealm.getAuthenticationInfo(token);
    }


}
