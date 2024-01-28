package have.somuch.regsys.api.shiro.matcher;

import have.somuch.regsys.api.shiro.token.NumberToken;
import have.somuch.regsys.common.utils.CommonUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import java.nio.charset.StandardCharsets;

public class TeacherCredentialsMatcher extends SimpleCredentialsMatcher {

    /*加密盐*/
    private String salt;

    public TeacherCredentialsMatcher(String salt) {
        super();
        this.salt = salt;
    }

    /**
     * 密码凭据校验
     * 需前端MD5 32位加密后传递
     *
     * @param token 认证Token
     * @param info  用户信息
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        NumberToken userToken = (NumberToken) token;
        Object accountCredentials = getCredentials(info);
        // 用户提交的密码
        String formCredentials = String.valueOf(userToken.getCredentials());
        if (accountCredentials == null || formCredentials == null) {
            return false;
        }
        String md5 = CommonUtils.md5(formCredentials.getBytes(StandardCharsets.UTF_8));
        String pwd = CommonUtils.md5((md5 + salt).getBytes(StandardCharsets.UTF_8));
        if (pwd == null) {
            return false;
        }
        pwd = pwd.toUpperCase();
        return equals(accountCredentials, pwd);
    }
}
