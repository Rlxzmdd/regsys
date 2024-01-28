package have.somuch.regsys.api.shiro.realm;


import have.somuch.regsys.api.shiro.token.NumberToken;
import have.somuch.regsys.api.user.entity.UserStudent;
import have.somuch.regsys.api.user.service.IUserStudentService;
import have.somuch.regsys.common.exception.user.CaptchaException;
import have.somuch.regsys.common.exception.user.UserNotExistsException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class StuNumberRealm extends AuthorizingRealm {

    @Autowired
    private IUserStudentService studentService;

    /**
     * 支持Token 适配
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof NumberToken;
    }

    /**
     * 授权，此Realm只负责校验学号+姓名的凭据，并返回Token
     * 授权操作在TokenRealm中实现
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String stuNumber = String.valueOf(authenticationToken.getPrincipal());
        String realName = String.valueOf(authenticationToken.getCredentials());
        UserStudent UserStudent = null;
        try {
//            UserStudent = studentService.login(stuNumber, realName);
        } catch (CaptchaException e) {
            throw new AuthenticationException(e.getMessage(), e);
        } catch (UserNotExistsException e) {
            throw new UnknownAccountException(e.getMessage(), e);
        } catch (IncorrectCredentialsException e) {
            throw new IncorrectCredentialsException(e.getMessage(), e);
        } catch (LockedAccountException e) {
            throw new LockedAccountException(e.getMessage(), e);
        } catch (ExcessiveAttemptsException e) {
            throw new ExcessiveAttemptsException(e.getMessage(), e);
        } catch (Exception e) {
            log.info("对用户[" + stuNumber + "]进行登录验证..验证未通过{}", e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }

        // 创建验证信息对象
        return new SimpleAuthenticationInfo(
                UserStudent,
                UserStudent.getRealName(),
                getName()
        );
    }
}
