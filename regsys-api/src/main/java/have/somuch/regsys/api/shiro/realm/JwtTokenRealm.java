package have.somuch.regsys.api.shiro.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import have.somuch.regsys.api.common.constant.Constant;
import have.somuch.regsys.api.common.constant.ResultCodeEnum;
import have.somuch.regsys.api.common.utils.JwtUtil;
import have.somuch.regsys.api.shiro.principal.WechatProgramUserPrincipal;
import have.somuch.regsys.api.shiro.token.WechatUserRequestToken;
import have.somuch.regsys.api.user.entity.UserStudent;
import have.somuch.regsys.api.user.entity.UserTeacher;
import have.somuch.regsys.api.user.service.IUserStudentService;
import have.somuch.regsys.api.user.service.IUserTeacherService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class JwtTokenRealm extends AuthorizingRealm {

    /*通过Bean聚合*/
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IUserStudentService studentService;

    @Autowired
    private IUserTeacherService teacherService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof WechatUserRequestToken;
    }

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 在doGetAuthenticationInfo 方法成功后，将携带credentials 执行此方法
        WechatProgramUserPrincipal wechatProgramUserPrincipal = (WechatProgramUserPrincipal) principals.getPrimaryPrincipal();
        String number = wechatProgramUserPrincipal.getNumber();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // todo 权限管理
//        List<String> permissionList = wechatMiniProgramRoleMapper.getPermissionList(number, wechatProgramUserPrincipal.getType());
//        for (String permission : permissionList) {
//            if (!StringUtils.isEmpty(permission)) {
//                authorizationInfo.addStringPermission(permission);
//            }
//        }
        return authorizationInfo;
    }

    /**
     * 认证用户的Token
     *
     * @param token 用户认证Token ，类型为WechatStudentUserRequestToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        WechatUserRequestToken requestToken = (WechatUserRequestToken) token;
        String userToken = String.valueOf(requestToken.getCredentials());
        if (userToken == null) {
            throw new AuthenticationException(ResultCodeEnum.USER_ERROR_A0303.getDescription());
        }
        // 根据生成密钥验证Token真伪与有效性
        if (!jwtUtil.verify(userToken)) {
            throw new AuthenticationException(ResultCodeEnum.USER_ERROR_A0304.getDescription());
        }
        String type = jwtUtil.getUserType(userToken);
        String user_id = jwtUtil.getUserId(userToken);
        // Token 中未携带用户类型情况
        if (type == null || user_id == null) {
            throw new AuthenticationException(ResultCodeEnum.USER_ERROR_A0300.getDescription());
        }

        WechatProgramUserPrincipal wechatProgramUserPrincipal;
        if (Constant.TOKEN_USER_TYPE_STUDENT.equals(type)) {
            QueryWrapper<UserStudent> wrapper = new QueryWrapper<>();
            wrapper.eq("stu_number", user_id);
            wrapper.eq("mark", 1);
            UserStudent student = studentService.getOne(wrapper);
            if (student == null) {
                throw new UnknownAccountException(ResultCodeEnum.USER_ERROR_A0201.getDescription());
            }
            wechatProgramUserPrincipal = new WechatProgramUserPrincipal().setNumber(student.getStuNumber()).setType(Constant.TOKEN_USER_TYPE_STUDENT);
        } else if (Constant.TOKEN_USER_TYPE_TEACHER.equals(type)) {
            QueryWrapper<UserTeacher> wrapper = new QueryWrapper<>();
            wrapper.eq("tch_number", user_id);
            wrapper.eq("mark", 1);
            UserTeacher teacher = teacherService.getOne(wrapper);
            if (teacher == null) {
                throw new UnknownAccountException(ResultCodeEnum.USER_ERROR_A0201.getDescription());
            }
            wechatProgramUserPrincipal = new WechatProgramUserPrincipal().setNumber(teacher.getTchNumber()).setType(Constant.TOKEN_USER_TYPE_TEACHER);
        } else {
            throw new AuthenticationException(ResultCodeEnum.USER_ERROR_A0305.getDescription());
        }

        return new SimpleAuthenticationInfo(wechatProgramUserPrincipal, token, getName());
    }


}
