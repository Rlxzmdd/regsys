package have.somuch.regsys.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import have.somuch.regsys.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 登录日志表
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_login_log")
public class LoginLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 日志标题
     */
    private String title;

    /**
     * 登录账号
     */
    private String username;

    /**
     * 登录IP地址
     */
    private String loginIp;

    /**
     * 登录地区
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 登录状态：0成功 1失败
     */
    private Integer status;

    /**
     * 操作类型：1登录系统 2退出系统
     */
    private Integer type;

    /**
     * 登录备注
     */
    private String note;

}
