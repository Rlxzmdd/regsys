package have.somuch.regsys.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import have.somuch.regsys.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 字典项管理表
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dict_data")
public class DictData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字典项名称
     */
    private String name;

    /**
     * 字典项值
     */
    private String value;

    /**
     * 字典类型ID
     */
    private Integer dictId;

    /**
     * 备注
     */
    private String note;

    /**
     * 显示顺序
     */
    private Integer sort;


}
