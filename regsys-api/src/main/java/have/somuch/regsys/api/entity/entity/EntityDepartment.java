package have.somuch.regsys.api.entity.entity;

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
 * 部门信息表
 * </p>
 *
 * @author isZhous
 * @since 2024-01-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("entity_department")
public class EntityDepartment extends BaseEntity {

    private static final long serialVersionUID = 1L;

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

}