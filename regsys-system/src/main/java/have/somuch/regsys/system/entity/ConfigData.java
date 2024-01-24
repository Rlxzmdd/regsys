package have.somuch.regsys.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("sys_config_data")
public class ConfigData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 配置标题
     */
    private String title;

    /**
     * 配置编码
     */
    private String code;

    /**
     * 配置值
     */
    private String value;

    /**
     * 配置(多图列表专用)
     */
    @TableField(exist = false)
    private String[] valueList;

    /**
     * 配置项
     */
    private String options;

    /**
     * 配置ID
     */
    private Integer configId;

    /**
     * 配置类型
     */
    private String type;

    /**
     * 状态：1正常 2停用
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 配置说明
     */
    private String note;


}
