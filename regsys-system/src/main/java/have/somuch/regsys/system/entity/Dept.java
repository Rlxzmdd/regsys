package have.somuch.regsys.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import have.somuch.regsys.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dept")
public class Dept extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 上级ID
     */
    private Integer pid;

    /**
     * 部门编号
     */
    private String code;

    /**
     * 部门类型：1公司 2子公司 3部门 4小组
     */
    private Integer type;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注说明
     */
    private String note;


}
