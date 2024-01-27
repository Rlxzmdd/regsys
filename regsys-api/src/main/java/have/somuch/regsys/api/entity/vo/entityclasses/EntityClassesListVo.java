package have.somuch.regsys.api.entity.vo.entityclasses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 班级信息表列表Vo
 * </p>
 *
 * @author isZhous
 * @since 2024-01-27
 */
@Data
public class EntityClassesListVo {

    /**
    * 班级信息表ID
    */
    private Integer id;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 班主任工号
     */
    private String teacherNumber;

    /**
     * 辅导员工号
     */
    private String counselorNumber;

    /**
     * 助辅学号
     */
    private String assistantNumber;

    /**
     * 所属学院索引->entity_college
     */
    private Integer collegeId;

    /**
     * 所属专业索引->entity_major
     */
    private Integer majorId;

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