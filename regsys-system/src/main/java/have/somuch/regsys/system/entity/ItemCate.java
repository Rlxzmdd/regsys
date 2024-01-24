package have.somuch.regsys.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import have.somuch.regsys.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 栏目管理表
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_item_cate")
public class ItemCate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 栏目名称
     */
    private String name;

    /**
     * 父级ID
     */
    private Integer pid;

    /**
     * 站点ID
     */
    private Integer itemId;

    /**
     * 拼音(全)
     */
    private String pinyin;

    /**
     * 拼音(简)
     */
    private String code;

    /**
     * 是否有封面：1有封面 2无封面
     */
    private Integer isCover;

    /**
     * 封面
     */
    private String cover;

    /**
     * 状态：1启用 2停用
     */
    private Integer status;

    /**
     * 备注
     */
    private String note;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否含有子级
     */
    @TableField(exist = false)
    private boolean hasChildren;

}
