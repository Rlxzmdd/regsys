package have.somuch.regsys.api.entity.vo.entitymajor;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 专业信息表表单Vo
 * </p>
 *
 * @author isZhous
 * @since 2024-01-27
 */
@Data
public class EntityMajorInfoVo {

    /**
     * 专业信息表ID
     */
    private Integer id;

    /**
     * 专业全称
     */
    private String majName;

    /**
     * 专业简称
     */
    private String majAbbr;

    /**
     * 专业所属学院->entity_college
     */
    private Integer collegeId;

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