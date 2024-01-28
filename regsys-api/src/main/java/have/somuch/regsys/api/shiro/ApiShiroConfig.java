package have.somuch.regsys.api.shiro;

import have.somuch.regsys.api.shiro.auth.CustomModularRealmAuthenticator;
import have.somuch.regsys.api.shiro.auth.RealmAuthorizer;
import have.somuch.regsys.api.shiro.matcher.JwtCredentialsMatcher;
import have.somuch.regsys.api.shiro.matcher.StudentCredentialsMatcher;
import have.somuch.regsys.api.shiro.matcher.TeacherCredentialsMatcher;
import have.somuch.regsys.api.shiro.realm.JwtTokenRealm;
import have.somuch.regsys.api.shiro.realm.StudentNumberRealm;
import have.somuch.regsys.api.shiro.realm.TeacherNumberRealm;
import lombok.Data;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Shiro配置类
 */
@Configuration
@Data
public class ApiShiroConfig {

    private final String CACHE_KEY = "shiro:cache:";
    private final String SESSION_KEY = "shiro:session:";
    private Integer EXPIRE = 86400 * 7;

    @Value("${login.secret}")
    private String secretKey;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.timeout}")
    private Integer timeout;


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> map = new HashMap<>();
        //登出
        map.put("/logout", "logout");
        //对所有用户认证
        map.put("/**", "authc");
        //登录
        shiroFilterFactoryBean.setLoginUrl("/login");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    @Bean
    public ModularRealmAuthenticator customModularRealmAuthenticator() {
        CustomModularRealmAuthenticator authenticator = new CustomModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return authenticator;
    }

    @Bean
    public StudentNumberRealm studentRealm() {
        StudentNumberRealm realm = new StudentNumberRealm();
        realm.setCredentialsMatcher(new StudentCredentialsMatcher());
        return realm;
    }
    @Bean
    public TeacherNumberRealm teacherRealm() {
        TeacherNumberRealm teacherRealm = new TeacherNumberRealm();
        teacherRealm.setCredentialsMatcher(new TeacherCredentialsMatcher(secretKey));
        return teacherRealm;
    }
    @Bean
    public JwtTokenRealm jwtTokenRealm() {
        JwtTokenRealm realm = new JwtTokenRealm();
        // 注入自定义的JwtCredentialsMatcher校验器，否则会使用CustomCredentialsMatcher
        realm.setCredentialsMatcher(new JwtCredentialsMatcher());
        return realm;
    }
    @Bean
    public RealmAuthorizer realmAuthorizer() {
        return new RealmAuthorizer();
    }


}
