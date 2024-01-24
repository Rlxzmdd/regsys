package have.somuch.regsys.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import have.somuch.regsys.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 字典类型表
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dict")
public class Dict extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典值
     */
    private String code;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 字典备注
     */
    private String note;


}
