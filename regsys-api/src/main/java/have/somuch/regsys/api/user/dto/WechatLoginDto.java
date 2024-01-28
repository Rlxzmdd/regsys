package have.somuch.regsys.api.user.dto;

import have.somuch.regsys.api.user.LoginType;
import have.somuch.regsys.api.user.service.IUserStudentService;
import have.somuch.regsys.api.user.service.IUserTeacherService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 小程序所有用户登录Dto
 *
 * @author iszhous
 */
@Component
public class WechatLoginDto {
    /*
    变量 type: stuNumber、examNum、tchNumber
    变量 number
    变量 password
    变量 examNum
     */
    @Autowired
    private IUserStudentService studentService;

    @Autowired
    private IUserTeacherService teacherService;

    @Getter
    @Setter
    private LoginType type;

    @Getter
    @Setter
    private String number;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String examNum;


}
