package have.somuch.regsys.api.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import com.fasterxml.jackson.annotation.JsonFormat;
import have.somuch.regsys.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * <p>
 * 
 * </p>
 *
 * @author isZhous
 * @since 2024-01-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user_teacher")
public class UserTeacher extends BaseEntity {

    private static final long serialVersionUID = 1L;

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

}