package have.somuch.regsys.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 后台用户管理表
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 职级ID
     */
    private Integer levelId;

    /**
     * 岗位ID
     */
    private Integer positionId;

    /**
     * 省份编码
     */
    private String provinceCode;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 区县编码
     */
    private String districtCode;

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
     * 有效标识
     */
    private Integer mark;

    /**
     * 角色ID
     */
    @TableField(exist = false)
    private Integer[] roleIds;

    /**
     * 城市集合
     */
    @TableField(exist = false)
    private String[] city;

}
