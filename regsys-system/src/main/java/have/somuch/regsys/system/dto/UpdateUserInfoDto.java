package have.somuch.regsys.system.dto;

import lombok.Data;

/**
 * 更新个人资料
 */
@Data
public class UpdateUserInfoDto {

    /**
     * 个人头像
     */
    private String avatar;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private String gender;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 个人简介
     */
    private String intro;

    /**
     * 个性签名
     */
    private String signature;

}
