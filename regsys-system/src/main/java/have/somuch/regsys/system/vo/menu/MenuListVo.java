package have.somuch.regsys.system.vo.menu;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 菜单列表Vo
 */
@Data
public class MenuListVo {

    /**
     * 菜单ID
     */
    private Integer id;

    /**
     * 父级ID
     */
    private Integer pid;

    /**
     * 菜单标题
     */
    private String title;

    /**
     * 图标
     */
    private String icon;

    /**
     * 菜单路径
     */
    private String path;

    /**
     * 菜单组件
     */
    private String component;

    /**
     * 目标
     */
    private String target;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 类型：1目录 2菜单 3节点
     */
    private Integer type;

    /**
     * 是否显示：1显示 2不显示
     */
    private Integer status;

    /**
     * 状态：0显示 1不显示
     */
    private Integer hide;

    /**
     * 备注
     */
    private String note;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 添加人
     */
    private Integer createUser;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新人
     */
    private Integer updateUser;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 有效标识
     */
    private Integer mark;

    /**
     * 是否有子级
     */
    private boolean hasChildren;

}
