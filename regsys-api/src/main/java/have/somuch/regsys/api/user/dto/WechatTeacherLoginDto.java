package have.somuch.regsys.api.user.dto;

import lombok.Data;

/**
 * 微信教师登录Dto
 */
@Data
public class WechatTeacherLoginDto {
    /*教师工号*/
    private String tchNumber;
    /*登录密码*/
    private String password;
}
