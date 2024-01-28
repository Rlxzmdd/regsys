package have.somuch.regsys.api.user.service.impl;

import have.somuch.regsys.api.common.constant.Constant;
import have.somuch.regsys.api.common.dto.WechatProgramIdentityDto;
import have.somuch.regsys.api.common.utils.JsonResultS;
import have.somuch.regsys.api.common.utils.JwtUtil;
import have.somuch.regsys.api.common.utils.WxUserRegisterUtil;
import have.somuch.regsys.api.shiro.realm.JwtTokenRealm;
import have.somuch.regsys.api.shiro.realm.StuNumberRealm;
import have.somuch.regsys.api.shiro.realm.TchNumberRealm;
import have.somuch.regsys.api.user.LoginType;
import have.somuch.regsys.api.user.dto.WechatLoginDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Slf4j
class UserWechatServiceImplTest {


    @InjectMocks
    private UserWechatServiceImpl wechatService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private WxUserRegisterUtil wxUserRegisterUtil;

    @BeforeEach
    void setUp() throws Exception {
        // 初始化mock对象
        MockitoAnnotations.initMocks(this);

        //1.构建一个SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(new JwtTokenRealm());
        defaultSecurityManager.setRealm(new StuNumberRealm());
        defaultSecurityManager.setRealm(new TchNumberRealm());
        //2.设置SecurityManager到运行环境中
        SecurityUtils.setSecurityManager(defaultSecurityManager);
    }

    /**
     * 测试登录
     */
    @Test
    void testLogin() {
        String code = "qweqwe";
        WechatLoginDto dto = new WechatLoginDto();
        dto.setType(LoginType.STU_NUMBER);
        dto.setNumber("S2021001");
        dto.setPassword("123123");

        // 仿真执行 requestCode2Session
        WechatProgramIdentityDto wxIdentity = new WechatProgramIdentityDto();
        wxIdentity.setUnionId("oltZs5EQE7YXXmt-tq55uTMYafAk");
        wxIdentity.setSessionKey("nhYCm8Fx8aKfy62tbpQFHg==");
        when(wxUserRegisterUtil.requestCode2Session(code)).thenReturn(wxIdentity);
        // 仿真执行 sign
        String jwtToken = "testToken";
        when(jwtUtil.sign(anyString(), anyString())).thenReturn(jwtToken);

        JsonResultS result = wechatService.login(code, dto);
        log.info(result.getData().toString());

        assertEquals(jwtToken, ((Map<String, String>) result.getData()).get("authorization"));
    }
    @Test
    public void testLogin_Success() {
        String code = "testCode";
        WechatLoginDto dto = new WechatLoginDto();
        dto.setType(LoginType.STU_NUMBER);
        dto.setNumber("S2021001");
        dto.setPassword("123123");

        WechatProgramIdentityDto wxIdentity = new WechatProgramIdentityDto();
        wxIdentity.setUnionId("oltZs5EQE7YXXmt-tq55uTMYafAk");
        wxIdentity.setSessionKey("nhYCm8Fx8aKfy62tbpQFHg==");

        when(wxUserRegisterUtil.requestCode2Session(code)).thenReturn(wxIdentity);
        String jwtToken = "testToken";
        when(jwtUtil.sign(anyString(), anyString())).thenReturn(jwtToken);

        JsonResultS result = wechatService.login(code, dto);

        verify(wxUserRegisterUtil).requestCode2Session(code);
        verify(jwtUtil).sign(dto.getNumber(), Constant.TOKEN_USER_TYPE_STUDENT);
        Assertions.assertEquals(JsonResultS.SUCCESS.getCode(), result.getCode());
        Assertions.assertEquals("{authorization=testToken}", result.getData().toString());
    }
    @Test
    public void testLogin_UnknownLoginType() {
        String code = "testCode";
        WechatLoginDto dto = new WechatLoginDto();
        dto.setType(LoginType.UNKNOWN);

        WechatProgramIdentityDto wxIdentity = new WechatProgramIdentityDto();
        wxIdentity.setUnionId("oltZs5EQE7YXXmt-tq55uTMYafAk");
        wxIdentity.setSessionKey("nhYCm8Fx8aKfy62tbpQFHg==");

        when(wxUserRegisterUtil.requestCode2Session(code)).thenReturn(wxIdentity);

        JsonResultS result = wechatService.login(code, dto);

        verify(wxUserRegisterUtil, times(1)).requestCode2Session(code);

        Assertions.assertEquals(JsonResultS.ERROR.getCode(), result.getCode());
        Assertions.assertEquals("未知的登录类型", result.getMsg());
    }

    @Test
    public void testLogin_NullWechatIdentity() {
        String code = "testCode";
        WechatLoginDto dto = new WechatLoginDto();
        dto.setType(LoginType.STU_NUMBER);
        dto.setNumber("testNumber");

        when(wxUserRegisterUtil.requestCode2Session(code)).thenReturn(null);

        JsonResultS result = wechatService.login(code, dto);

        verify(wxUserRegisterUtil, times(1)).requestCode2Session(code);

        Assertions.assertEquals(JsonResultS.ERROR.getCode(), result.getCode());
        Assertions.assertEquals("用户错误-A0402", result.getMsg());
    }

}