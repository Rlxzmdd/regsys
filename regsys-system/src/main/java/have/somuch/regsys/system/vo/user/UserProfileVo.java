package have.somuch.regsys.system.vo.user;

import lombok.Data;

/**
 * 个人中心Vo
 */
@Data
public class UserProfileVo {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别:1男 2女 3保密
     */
    private Integer gender;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 详细地址
     */
    private String address;


    /**
     * 个人简介
     */
    private String intro;

}
