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
 * 班级信息表
 * </p>
 *
 * @author isZhous
 * @since 2024-01-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("entity_classes")
public class EntityClasses extends BaseEntity {

    private static final long serialVersionUID = 1L;

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

}