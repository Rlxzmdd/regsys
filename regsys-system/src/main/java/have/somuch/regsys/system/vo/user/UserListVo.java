package have.somuch.regsys.system.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import have.somuch.regsys.system.entity.Role;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 用户列表Vo
 */
@Data
public class UserListVo {

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
     * 性别描述
     */
    private String genderName;

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
     * 出生日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    /**
     * 部门ID
     */
    private Integer deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 职级ID
     */
    private Integer levelId;

    /**
     * 职级名称
     */
    private String levelName;

    /**
     * 岗位ID
     */
    private Integer positionId;

    /**
     * 岗位名称
     */
    private String positionName;

    /**
     * 省份ID
     */
    private String provinceCode;

    /**
     * 市区ID
     */
    private String cityCode;

    /**
     * 区县ID
     */
    private String districtCode;

    /**
     * 城市集合
     */
    private String[] city;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 所属城市
     */
    private String cityName;

    /**
     * 登录用户名
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 盐加密
     */
    private String salt;

    /**
     * 个人简介
     */
    private String intro;

    /**
     * 状态：1正常 2禁用
     */
    private Integer status;

    /**
     * 备注
     */
    private String note;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 登录次数
     */
    private Integer loginNum;

    /**
     * 最近登录IP
     */
    private String loginIp;

    /**
     * 最近登录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;

    /**
     * 添加人
     */
    private Integer createUser;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新人
     */
    private Integer updateUser;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 有效标识(1正常 0删除)
     */
    private Integer mark;

    /**
     * 角色名称
     */
    private List<Role> roles;

}
