package have.somuch.regsys.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import have.somuch.regsys.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 配置表
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_config")
public class Config extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 配置名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String note;

}
