package have.somuch.regsys.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import have.somuch.regsys.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 职级表
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_level")
public class Level extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 职级名称
     */
    private String name;

    /**
     * 状态：1正常 2停用
     */
    private Integer status;

    /**
     * 显示顺序
     */
    private Integer sort;

}
