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
 * 专业信息表
 * </p>
 *
 * @author isZhous
 * @since 2024-01-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("entity_major")
public class EntityMajor extends BaseEntity {

    private static final long serialVersionUID = 1L;

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

}