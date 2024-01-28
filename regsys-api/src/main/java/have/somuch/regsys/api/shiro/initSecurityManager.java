package have.somuch.regsys.api.shiro;

import have.somuch.regsys.api.shiro.auth.RealmAuthorizer;
import have.somuch.regsys.api.shiro.realm.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * 在Bean 加载完成后获取Shiro securityManager 注入registration 模块中自定义的realm
 * run() 方法在Bean 加载完成后自动执行
 * @author Cheney
 */

@Component
@Order(2)
@Slf4j
public class initSecurityManager implements CommandLineRunner {
    @Autowired
    private SecurityManager securityManager;

    @Autowired
    private ModularRealmAuthenticator customModularRealmAuthenticator;

    @Autowired
    private RealmAuthorizer realmAuthorizer;

    @Autowired
    private StuExamRealm stuExamRealm;
    @Autowired
    private StuNumberRealm studentRealm;
    @Autowired
    private TchNumberRealm teacherRealm;
    @Autowired
    private JwtTokenRealm jwtTokenRealm;

//    @Autowired
//    private WechatStudentALRealm studentALRealm;
//
//    @Autowired
//    private MyShiroRealm myShiroRealm;



//    @Autowired
//    private WechatStudentExamRealm studentExamRealm;

    @Override
    public void run(String... args) {
        // 注入Realm 到 Shiro SecurityManager 中
        List<Realm> realmList = new ArrayList<>();
        realmList.add(stuExamRealm);
        realmList.add(studentRealm);
        realmList.add(teacherRealm);
        realmList.add(jwtTokenRealm);
        realmAuthorizer.setRealms(realmList);

        org.apache.shiro.mgt.DefaultSecurityManager manager = (org.apache.shiro.mgt.DefaultSecurityManager) securityManager;
        manager.setAuthenticator(customModularRealmAuthenticator);
        manager.setAuthorizer(realmAuthorizer);
        // 加载顺序 Authenticator -> realms

        manager.setRealms(realmList);
    }
}
