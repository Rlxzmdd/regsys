package have.somuch.regsys.api.user.vo.useraccount;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 用户账号信息表列表Vo
 * </p>
 *
 * @author isZhous
 * @since 2024-01-29
 */
@Data
public class UserAccountListVo {

    /**
    * 用户账号信息表ID
    */
    private Integer id;

    /**
     * 信息类型
     */
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 绑定类型: 0学生 1教师
     */
    private Integer bindType;


    /**
    * 绑定类型描述
    */
    private String bindTypeName;

    /**
     * 绑定工号
     */
    private String bindNumber;

    /**
     * 创建记录用户
     */
    private Integer createUser;

    /**
     * 更新记录用户
     */
    private Integer updateUser;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 记录有效性
     */
    private Integer mark;

}