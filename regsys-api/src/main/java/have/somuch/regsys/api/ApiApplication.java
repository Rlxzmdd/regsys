package have.somuch.regsys.api;

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
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
        System.out.println("新生报到系统小程序API已启动");
    }
}
