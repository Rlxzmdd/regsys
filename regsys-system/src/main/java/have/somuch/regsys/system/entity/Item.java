package have.somuch.regsys.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import have.somuch.regsys.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 站点配置表
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_item")
public class Item extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 站点名称
     */
    private String name;

    /**
     * 站点类型:1普通站点 2其他
     */
    private Integer type;

    /**
     * 站点地址
     */
    private String url;

    /**
     * 站点图片
     */
    private String image;

    /**
     * 是否二级域名:1是 2否
     */
    private Integer isDomain;

    /**
     * 状态：1在用 2停用
     */
    private Integer status;

    /**
     * 站点备注
     */
    private String note;

    /**
     * 显示顺序
     */
    private Integer sort;


}
