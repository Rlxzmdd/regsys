package have.somuch.regsys.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import have.somuch.regsys.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 广告位管理表
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_ad_sort")
public class AdSort extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 广告位名称
     */
    private String name;

    /**
     * 广告位描述
     */
    private String description;

    /**
     * 站点ID
     */
    private Integer itemId;

    /**
     * 栏目ID
     */
    private Integer cateId;

    /**
     * 广告页面位置
     */
    private Integer locId;

    /**
     * 站点类型：1PC网站 2WAP手机站 3微信小程序 4APP移动端
     */
    private Integer platform;

    /**
     * 广告位排序
     */
    private Integer sort;

}
