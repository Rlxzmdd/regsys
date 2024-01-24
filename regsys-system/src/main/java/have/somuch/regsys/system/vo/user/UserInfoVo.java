package have.somuch.regsys.system.vo.user;

import have.somuch.regsys.system.entity.Menu;
import have.somuch.regsys.system.entity.Role;
import lombok.Data;

import java.util.List;

/**
 * 用户信息Vo
 */
@Data
public class UserInfoVo {

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

    /**
     * 角色列表
     */
    private List<Role> roles;

    /**
     * 菜单权限
     */
    private List<Menu> authorities;

    /**
     * 获取节点权限列表
     */
    private List<String> permissionList;

}
