package have.somuch.regsys.api.user.vo.userstudent;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 学生信息表列表Vo
 * </p>
 *
 * @author isZhous
 * @since 2024-01-27
 */
@Data
public class UserStudentListVo {

    /**
    * 学生信息表ID
    */
    private Integer id;

    /**
     * 学生姓名
     */
    private String realName;

    /**
     * 学生学号
     */
    private String stuNumber;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 性别
     */
    private String gender;

    /**
     * 民族
     */
    private String nation;

    /**
     * 邮箱
     */
    private String email;

    /**
     * QQ号
     */
    private String qq;

    /**
     * 政治面貌
     */
    private String politic;

    /**
     * 通知书编号
     */
    private String serialNumber;

    /**
     * 考生号
     */
    private String examNumber;

    /**
     * 学生状态
     */
    private String stuStatus;

    /**
     * 所属学院索引->entity_college
     */
    private Integer collegeId;

    /**
     * 所属专业索引->entity_major
     */
    private Integer majorId;

    /**
     * 所属班级索引->entity_classes
     */
    private Integer classesId;

    /**
     * 学生头像照片
     */
    private String avatar;

    /**
     * 记录创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 记录更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 创建记录用户
     */
    private Integer createUser;

    /**
     * 更新记录用户
     */
    private Integer updateUser;

    /**
     * 记录有效性
     */
    private Integer mark;

}