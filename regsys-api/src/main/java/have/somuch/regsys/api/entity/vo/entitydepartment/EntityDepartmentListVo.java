package have.somuch.regsys.api.entity.vo.entitydepartment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 部门信息表列表Vo
 * </p>
 *
 * @author isZhous
 * @since 2024-01-27
 */
@Data
public class EntityDepartmentListVo {

    /**
    * 部门信息表ID
    */
    private Integer id;

    /**
     * 创建时间
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
     * 部门所属学院->entity_college
     */
    private Integer collegeId;

    /**
     * 部门全称
     */
    private String depName;

    /**
     * 部门简称
     */
    private String depAbbr;

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