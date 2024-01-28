package have.somuch.regsys.api.user.service.impl;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

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
//        defaultSecurityManager.setRealm(new StuExamRealm());
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

}