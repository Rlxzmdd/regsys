package have.somuch.regsys.api.user.vo.userteacher;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 表单Vo
 * </p>
 *
 * @author isZhous
 * @since 2024-01-27
 */
@Data
public class UserTeacherInfoVo {

    /**
     * ID
     */
    private Integer id;

    /**
     * 教师姓名
     */
    private String realName;

    /**
     * 教师工号
     */
    private String tchNumber;

    /**
     * 性别
     */
    private String gender;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * QQ号
     */
    private String qq;

    /**
     * 办公室位置
     */
    private String office;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 教师其他职责身份
     */
    private String identity;

    /**
     * 所属学院索引->entity_college
     */
    private Integer collegeId;

    /**
     * 部门索引->entity_department
     */
    private Integer departmentId;

    /**
     * 创建记录用户
     */
    private Integer createUser;

    /**
     * 更新记录用户
     */
    private Integer updateUser;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 记录有效性
     */
    private Integer mark;

}