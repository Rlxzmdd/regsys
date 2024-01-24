package have.somuch.regsys.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"have.somuch.regsys.*"})
@MapperScan("have.somuch.regsys.**.mapper")
@EnableTransactionManagement
// 开启定时任务支持
@EnableScheduling
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  regsys_EleVue前后端分离旗舰版启动成功   ლ(´ڡ`ლ)ﾞ");

    }

}
