package have.somuch.regsys.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import have.somuch.regsys.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 演示案例二
 * </p>
 *
 * @author 鲲鹏
 * @since 2023-03-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_example2")
public class Example2 extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 案例名称
     */
    private String name;

    /**
     * 类型：1京东 2淘宝 3拼多多 4唯品会
     */
    private Integer type;

    /**
     * 状态：1正常 2停用
     */
    private Integer status;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String note;

}